package com.comp1786.m_expense;





import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.comp1786.m_expense.model.Expenses;
import com.comp1786.m_expense.model.Trip;
import com.comp1786.m_expense.model.Type;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "M-expenses";
    private static final String TABLE_TRIP_NAME = "Trips";
    private static final String TABLE_EXPENSES_NAME = "Expenses";
    private static final String TABLE_TYPE_NAME = "Types";

    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String DESTINATION_COLUMN = "destination";
    public static final String START_DATE_COLUMN = "start_Date";
    public static final String END_DATE_COLUMN = "end_Date";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String RISK_COLUMN = "risk";
    public static final String TYPE_COLUMN = "type";
    public static final String TYPE_ID_COLUMN = "type_Id";
    public static final String AMOUNT_COLUMN = "amount";
    public static final String DATE_COLUMN = "date";
    public static final String TIME_COLUMN = "time";
    public static final String COMMENT_COLUMN = "comment";
    public static final String LOCATION_COLUMN = "location";
    public static final String IMAGE_COLUMN = "image";
    public static final String TRIP_ID_COLUMN = "trip_Id";


    public SQLiteDatabase database;

    private static final String DATABASE_CREATE_TRIP = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s DATE, " +
                    "   %s DATE, " +
                    "   %s INTEGER, " +
                    "   %s INTEGER, " +
                    "   %s TEXT)",
            TABLE_TRIP_NAME, ID_COLUMN, NAME_COLUMN, DESTINATION_COLUMN, START_DATE_COLUMN,END_DATE_COLUMN,RISK_COLUMN,TYPE_COLUMN,DESCRIPTION_COLUMN);
    private static final String DATABASE_CREATE_TYPE = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT)",
            TABLE_TYPE_NAME, ID_COLUMN,NAME_COLUMN);

    private static final String DATABASE_CREATE_EXPENSES = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s INTEGER references %s (%s), " +
                    "   %s FLOAT, " +
                    "   %s DATE, " +
                    "   %s TIME, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s INTEGER references %s (%s))",
            TABLE_EXPENSES_NAME, ID_COLUMN, TYPE_ID_COLUMN,TABLE_TYPE_NAME,ID_COLUMN, AMOUNT_COLUMN, DATE_COLUMN,TIME_COLUMN,COMMENT_COLUMN,LOCATION_COLUMN,IMAGE_COLUMN,NAME_COLUMN,TRIP_ID_COLUMN,TABLE_TRIP_NAME,TRIP_ID_COLUMN);
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TRIP);
        db.execSQL(DATABASE_CREATE_TYPE);
        db.execSQL(DATABASE_CREATE_EXPENSES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);

        Log.v(this.getClass().getName(), DATABASE_NAME + " database upgrade to version " +
                newVersion + " - old data lost");
        onCreate(db);
    }
    public long addTrip(Trip trip) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME_COLUMN, trip.getName());
        rowValues.put(DESTINATION_COLUMN, trip.getDestination());
        rowValues.put(START_DATE_COLUMN,trip.getStart_Date());
        rowValues.put(END_DATE_COLUMN,trip.getEnd_Date() );
        rowValues.put(RISK_COLUMN, trip.getRisk());
        rowValues.put(TYPE_COLUMN, trip.getType());
        rowValues.put(DESCRIPTION_COLUMN,trip.getDescription());

        return database.insertOrThrow(TABLE_TRIP_NAME, null, rowValues);
    }
    public long addType(Type type) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(NAME_COLUMN, type.getName());

        return database.insertOrThrow(TABLE_TYPE_NAME, null, rowValues);
    }
    public long addExpense(Expenses expenses) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(TYPE_ID_COLUMN, expenses.getType_id());
        rowValues.put(AMOUNT_COLUMN,expenses.getAmount());
        rowValues.put(DATE_COLUMN,expenses.getDate() );
        rowValues.put(TIME_COLUMN, expenses.getTime());
        rowValues.put(COMMENT_COLUMN,expenses.getComment());
        rowValues.put(LOCATION_COLUMN,expenses.getLocation());
        rowValues.put(IMAGE_COLUMN,expenses.getImage());
        rowValues.put(NAME_COLUMN,expenses.getName());
        rowValues.put(TRIP_ID_COLUMN,expenses.getTrip_id());


        return database.insertOrThrow(TABLE_EXPENSES_NAME, null, rowValues);
    }
    public ArrayList<Expenses> getListExpensesByTripId(int Id){
        ArrayList<Expenses> expenses = new ArrayList<>();
        Cursor results = database.query(true, TABLE_EXPENSES_NAME, new String[] {ID_COLUMN, TYPE_ID_COLUMN, AMOUNT_COLUMN, DATE_COLUMN,TIME_COLUMN,COMMENT_COLUMN,LOCATION_COLUMN,IMAGE_COLUMN,NAME_COLUMN,TRIP_ID_COLUMN }, TRIP_ID_COLUMN+ " = ?",
                new String[] {String.valueOf(Id) }, null, null, null,
                null);

        results.moveToFirst();
        while (!results.isAfterLast()) {
            Expenses expense=new Expenses(results.getInt(0),results.getInt(1),results.getFloat(2),results.getString(3).toString(),results.getString(4).toString(),results.getString(5),results.getString(6),results.getString(7),results.getString(8),results.getInt(9));
            expenses.add(expense);
            results.moveToNext();
        }
        if(expenses==null){;
            return null;
        }else return expenses;
    }
    public Float getExpensesByTripId(int id){
        Float expenses=0f;
        Cursor results = database.query(TABLE_EXPENSES_NAME, new String[] {ID_COLUMN, TYPE_ID_COLUMN, AMOUNT_COLUMN, DATE_COLUMN,TIME_COLUMN,COMMENT_COLUMN,LOCATION_COLUMN,IMAGE_COLUMN,NAME_COLUMN,TRIP_ID_COLUMN }, TRIP_ID_COLUMN + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        results.moveToFirst();
        while (!results.isAfterLast()) {
            Expenses expense=new Expenses(results.getInt(0),results.getInt(1),results.getFloat(2),results.getString(3).toString(),results.getString(4).toString(),results.getString(5),results.getString(6),results.getString(7),results.getString(8),results.getInt(9));
            expenses+=expense.getAmount();
            results.moveToNext();
        }
        if(expenses==null){
            return null;
        }else return expenses;
    }
    public ArrayList<Trip> getListTrip() {
        ArrayList<Trip> trips = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TRIP_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery(selectQuery, null);
        results.moveToFirst();
        while (!results.isAfterLast()) {
            Float totalExpenses=getExpensesByTripId(results.getInt(0));
            Trip trip=new Trip(results.getInt(0),results.getString(1),results.getString(2),results.getString(3).toString(),results.getString(4).toString(),results.getInt(5),results.getString(7),results.getInt(6),totalExpenses);
            System.out.println(trip.toString());
            trips.add(trip);
            results.moveToNext();
        }
        if(trips==null){
            return null;
        }else return trips;

    }
    public ArrayList<Expenses> getListExpense() {
        ArrayList<Expenses> expenses = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSES_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery(selectQuery, null);
        results.moveToFirst();
        while (!results.isAfterLast()) {
            Expenses expense=new Expenses(results.getInt(0),results.getInt(1),results.getFloat(2),results.getString(3).toString(),results.getString(4).toString(),results.getString(5),results.getString(6),results.getString(7),results.getString(8),results.getInt(9));
            System.out.println(expense.toString());
            expenses.add(expense);
            results.moveToNext();
        }
        if(expenses==null){
            return null;
        }else return expenses;

    }
    public Float getTotalExpenses(){
        Float total=0f;
        String selectQuery = "SELECT  "+AMOUNT_COLUMN+" FROM " + TABLE_EXPENSES_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery(selectQuery, null);
        results.moveToFirst();
        while (!results.isAfterLast()) {

            total+=results.getFloat(0);
            results.moveToNext();
        }
        if(total==null){
            return null;
        }else return total;
    }
    public ArrayList<Type> getListType() {
        ArrayList<Type> types = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TYPE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results = db.rawQuery(selectQuery, null);
        results.moveToFirst();
        while (!results.isAfterLast()) {
            Type type=new Type(results.getInt(0),results.getString(1));
            System.out.println(type);
            types.add(type);
            results.moveToNext();
        }
        if(types==null){
            return null;
        }else return types;

    }
    public ArrayList<Trip> searchTripByName(String key){
        ArrayList<Trip> trips = new ArrayList<>();
        String[] parts = key.split(" ");
        String queryString = "";
        for(int i = 0; i < parts.length; i++) {
            queryString += NAME_COLUMN + " LIKE '%" + parts[i] + "%' OR ";
            queryString += DESTINATION_COLUMN+ " LIKE '%" + parts[i]+"%'";
            if(i != (parts.length - 1)) {
                queryString += " OR ";
            }
        }
        Cursor results = database.query(true, TABLE_TRIP_NAME, new String[] { ID_COLUMN, NAME_COLUMN, DESTINATION_COLUMN, START_DATE_COLUMN,END_DATE_COLUMN,RISK_COLUMN,DESCRIPTION_COLUMN,TYPE_COLUMN }, queryString,null,
                null, null, null,
                null);

        results.moveToFirst();
        while (!results.isAfterLast()) {
            Float totalExpenses=getExpensesByTripId(results.getInt(0));
            Trip trip=new Trip(results.getInt(0),results.getString(1),results.getString(2),results.getString(3).toString(),results.getString(4).toString(),results.getInt(5),results.getString(7),results.getInt(6),totalExpenses);
            System.out.println(trip);
            trips.add(trip);
            results.moveToNext();
        }
        if(trips==null){
            return null;
        }else return trips;

    }
    public ArrayList<Expenses> searchExpensesByName(String key){
        ArrayList<Expenses> expenses = new ArrayList<>();
        String[] parts = key.split(" ");
        String queryString = "";
        for(int i = 0; i < parts.length; i++) {
            queryString += NAME_COLUMN + " LIKE '%" + parts[i] + "%' OR ";
            queryString += LOCATION_COLUMN+ " LIKE '%" + parts[i]+"%'";
            if(i != (parts.length - 1)) {
                queryString += " OR ";
            }
        }
        Cursor results = database.query(true, TABLE_EXPENSES_NAME, new String[] {ID_COLUMN, TYPE_ID_COLUMN, AMOUNT_COLUMN, DATE_COLUMN,TIME_COLUMN,COMMENT_COLUMN,LOCATION_COLUMN,IMAGE_COLUMN,NAME_COLUMN,TRIP_ID_COLUMN }, queryString,null, null, null, null,
                null);

        results.moveToFirst();
        while (!results.isAfterLast()) {
            Expenses expense=new Expenses(results.getInt(0),results.getInt(1),results.getFloat(2),results.getString(3).toString(),results.getString(4).toString(),results.getString(5),results.getString(6),results.getString(7),results.getString(8),results.getInt(9));
            System.out.println(expense);
            expenses.add(expense);
            results.moveToNext();
        }
        if(expenses==null){
           return null;
        }else return expenses;

    }
    public Trip searchTripById(int id){
        database = this.getWritableDatabase();
        Cursor cursor = database.query(TABLE_TRIP_NAME, new String[] {ID_COLUMN, NAME_COLUMN, DESTINATION_COLUMN, START_DATE_COLUMN,END_DATE_COLUMN,RISK_COLUMN,DESCRIPTION_COLUMN,TYPE_COLUMN }, ID_COLUMN+ "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Trip trip = new Trip(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                Integer.parseInt(cursor.getString(5)),
                cursor.getString(6),
                cursor.getInt(7));
        cursor.close();
        database.close();
        return trip;
    }
    public Expenses searchExpenseById(int id){
        database=this.getWritableDatabase();
        Cursor results = database.query(TABLE_EXPENSES_NAME, new String[] {ID_COLUMN, TYPE_ID_COLUMN, AMOUNT_COLUMN, DATE_COLUMN,TIME_COLUMN,COMMENT_COLUMN,LOCATION_COLUMN,IMAGE_COLUMN,TRIP_ID_COLUMN }, ID_COLUMN+ "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        if (results != null)
            results.moveToFirst();
        Expenses expense=new Expenses(results.getInt(0),results.getInt(1),results.getFloat(2),results.getString(3).toString(),results.getString(4).toString(),results.getString(5),results.getString(6),results.getString(7),results.getString(8),results.getInt(9));
        results.close();
        database.close();
        return expense;
    }
    public Type searchTypeById(int id){
        database=this.getWritableDatabase();
        Cursor results = database.query(TABLE_TYPE_NAME, new String[] {ID_COLUMN,NAME_COLUMN }, ID_COLUMN+ "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);
        if (results != null)
            results.moveToFirst();
        Type type=new Type(results.getInt(0),results.getString(1));
        results.close();
        database.close();
        return type;
    }

    public int updateTrip(Trip trip,int id) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COLUMN, trip.getName());
        values.put(DESTINATION_COLUMN, trip.getDestination());
        values.put(START_DATE_COLUMN, trip.getStart_Date());
        values.put(END_DATE_COLUMN, trip.getEnd_Date());
        values.put(RISK_COLUMN, trip.getRisk());
        values.put(TYPE_COLUMN, trip.getType());
        values.put(DESCRIPTION_COLUMN, trip.getDescription());

        // updating row
        return database.update(TABLE_TRIP_NAME,
                values,
                ID_COLUMN + " = ?",
                new String[] { String.valueOf(id) });
    }

    public int updateExpenses(Expenses expenses,int id) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TYPE_ID_COLUMN, expenses.getType_id());
        values.put(AMOUNT_COLUMN, expenses.getAmount());
        values.put(DATE_COLUMN, expenses.getDate());
        values.put(TIME_COLUMN, expenses.getTime());
        values.put(COMMENT_COLUMN, expenses.getComment());
        values.put(LOCATION_COLUMN, expenses.getLocation());
        values.put(IMAGE_COLUMN, expenses.getImage());
        values.put(TRIP_ID_COLUMN, expenses.getTrip_id());
        values.put(NAME_COLUMN,expenses.getName());

        // updating row
        return database.update(TABLE_EXPENSES_NAME,
                values,
                ID_COLUMN + " = ?",
                new String[] { String.valueOf(id) });
    }

    public void deleteTripById(long id) {
        ArrayList<Expenses> expenses=new ArrayList<>();
        expenses=getListExpensesByTripId((int) id);
        for (Expenses ex:expenses) {
            deleteExpensesById(ex.getId());
        }
        database = this.getWritableDatabase();
        database.delete(TABLE_TRIP_NAME, ID_COLUMN + " = ?",
                new String[] { String.valueOf(id) });
        database.close();
    }

    public void deleteAllTrip(){
//        String deleteExpenses = "DELETE FROM " + TABLE_EXPENSES_NAME;
//        String deleteTrip = "DELETE FROM " + TABLE_TRIP_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        dropAndRecreateTrip(database);
    }
    public void deleteExpensesById(long id) {
        database = this.getWritableDatabase();
        database.delete(TABLE_EXPENSES_NAME, ID_COLUMN + " = ?",
                new String[] { String.valueOf(id) });
        database.close();
    }

    private void dropAndRecreateTrip(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP_NAME);
        dbCreate(db);
    }
    private void dropAndRecreateExpenses(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES_NAME);
        db.execSQL(DATABASE_CREATE_EXPENSES);
    }
    public void dbCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TRIP);
        db.execSQL(DATABASE_CREATE_EXPENSES);
    }

}

