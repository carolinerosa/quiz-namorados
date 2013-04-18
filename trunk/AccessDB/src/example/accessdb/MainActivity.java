package example.accessdb;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	  super.onCreate(savedInstanceState);
          dbMySQL dbmysql = new dbMySQL(this);
        
          dbmysql.conectarMySQL("db4free.net","3306","lovegame", "carolineroosa", "1719950101"); // ip do servidor mysql, porta, banco, usuário, senha
          dbmysql.queryMySQL();
          dbmysql.desconectarMySQL();
          
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
