package example.accessdb;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("ParserError")
public class dbMySQL extends Activity{
    private Connection conn = null;
    private Statement st;
    private ResultSet rs;
    private String sql;
    Context context;
    public dbMySQL(Context context){
        this.context = context;
      }

      public void conectarMySQL(String host, String porta, String banco, String usuario, String senha){
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            }catch(Exception erro){
                Log.e("MYSQL","Erro: "+erro);
            }
            try{
                conn=DriverManager.getConnection("jdbc:mysql://"+host+":"+porta+"/"+banco,usuario,senha);
                Log.i("MYSQL","Conectado.");
                Toast.makeText(context.getApplicationContext(), "MySQL conexão feita com sucesso.", Toast.LENGTH_SHORT).show(); 
            }catch(Exception erro){
                Log.e("MYSQL","Erro: "+erro);
                Toast.makeText(context.getApplicationContext(), "MySQL falha na conexão.", Toast.LENGTH_SHORT).show();
            }
        }
    public void desconectarMySQL(){
        try {
            conn.close();
            Log.i("MYSQL","Desconectado.");
        } catch (Exception erro) {
            Log.e("MYSQL","Erro: "+erro);
        }
    }
    
    public void queryMySQL(){
        try{
   
        	st=conn.createStatement();
            sql="SELECT * FROM Persons";
            rs=st.executeQuery(sql);
            rs.first();
            rs.absolute(2);
            Log.i("MYSQL","Resultado: "+rs.getString("LastName"));
        } catch (Exception erro){
            Log.e("MYSQL","Erro: "+erro);
        }
    }
    
}