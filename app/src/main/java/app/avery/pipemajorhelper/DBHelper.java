package app.avery.pipemajorhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static String dbName = "band";
    private String dbPath;
    private SQLiteDatabase bandDB;
    private final Context context;

    public DBHelper(Context context){
        super(context, dbName, null, DB_VERSION);
        this.context = context;
        dbPath = context.getFilesDir().getPath() + "/";
    }

    public void createDB(){
        this.getReadableDatabase();
        try {
            copyDB();
        }
        catch (Exception e){
            e.getMessage();
        }
    }

    public void copyDB(){
        try{
            if(!checkDB()){
                InputStream is = context.getResources().openRawResource(R.raw.pipeband);
                File aFile = new File(dbPath);
                if(!aFile.exists()){
                    aFile.mkdirs();
                }
                String op = dbPath + dbName + ".db";
                OutputStream os = new FileOutputStream(op);
                byte[] buffer = new byte[1024];
                int length;
                while((length = is.read(buffer)) > 0){
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            }
        }
        catch (Exception e){
            e.getMessage();
        }
    }

    public boolean NewUser(){
        SQLiteDatabase checkDB = null;
        boolean ret = false;
        try{
            String path = dbPath + dbName + ".db";
            File aFile = new File(path);
            if(aFile.exists()){
                checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
                if(checkDB != null){
                    Cursor tabChk = checkDB.rawQuery("SELECT BandName FROM sqlite_master where type='table' and name='Info';", null);
                    boolean playersTabExists = false;
                    if(tabChk != null){
                        tabChk.moveToNext();
                        playersTabExists = !tabChk.isAfterLast();
                        tabChk.close();
                    }
                    if(playersTabExists){
                        ret = true;
                    }
                }
            }
        }
        catch (SQLiteException e){
            e.getMessage();
        }
        if(checkDB != null){
            checkDB.close();
        }
        return ret;
    }

    private boolean checkDB(){
        SQLiteDatabase checkDB = null;
        boolean ret = false;
        try{
            String path = dbPath + dbName + ".db";
            File aFile = new File(path);
            if(aFile.exists()){
                checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
                if(checkDB != null){
                    Cursor tabChk = checkDB.rawQuery("SELECT name FROM sqlite_master where type='table' and name='Players';", null);
                    boolean playersTabExists = false;
                    if(tabChk != null){
                        tabChk.moveToNext();
                        playersTabExists = !tabChk.isAfterLast();
                        tabChk.close();
                    }
                    if(playersTabExists){
                        ret = true;
                    }
                }
            }
        }
        catch (SQLiteException e){
            e.getMessage();
        }
        if(checkDB != null){
            checkDB.close();
        }
        return ret;
    }

    public SQLiteDatabase openDB() throws SQLiteException{
        String myPath = dbPath + dbName + ".db";
        if(checkDB()){
            bandDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }
        else{
            try{
                this.copyDB();
                bandDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            }
            catch(Exception e){
                e.getMessage();
            }
        }
        return bandDB;
    }

    @Override
    public synchronized void close(){
        if(bandDB != null){
            bandDB.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
