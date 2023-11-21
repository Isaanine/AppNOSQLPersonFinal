package com.example.appnosqlperson.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appnosqlperson.model.PersonModel

class DBHandler // creating a constructor for our database handler.
    (context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    // below method is for creating a database by running a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // on below line we are creating an sqlite query and we are
        // setting our column names along with their data types.
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NOME_COL + " TEXT,"
                + ENDERECO_COL + " TEXT,"
                + BAIRRO_COL + " TEXT,"
                + CEP_COL + " TEXT,"
                + CIDADE_COL + " TEXT,"
                + ESTADO_COL + " TEXT,"
                + TEL_COL + " TEXT,"
                + CEL_COL + " TEXT)")

        // at last we are calling a exec sql method to execute above sql query
        db.execSQL(query)
    }

    // this method is use to add new course to our sqlite database.
    fun addNewPerson(
        personname: String?,
        personendereco: String?,
        personbairro: String?,
        personcep: String?,
        personcidade: String?,
        personestado: String?,
        persontel: String?,
        personcel: String?
    ) {
        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        val db = this.writableDatabase
        // on below line we are creating a
        // variable for content values.
        val values = ContentValues()
        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NOME_COL, personname)
        values.put(ENDERECO_COL, personendereco)
        values.put(BAIRRO_COL, personbairro)
        values.put(CEP_COL, personcep)
        values.put(CIDADE_COL, personcidade)
        values.put(ESTADO_COL, personestado)
        values.put(TEL_COL, persontel)
        values.put(CEL_COL, personcel)
        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values)
        // at last we are closing our
        // database after adding database.
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        // creating a constant variables for our database.
        // below variable is for our database name.
        private const val DB_NAME = "coursedb"

        // below int is our database version
        private const val DB_VERSION = 1

        // below variable is for our table name.
        private const val TABLE_NAME = "mycourses"

        // below variable is for our id column.
        private const val ID_COL = "id"

        // below variable is for our course name column
        private const val NOME_COL = "name"

        // below variable id for our course duration column.
        private const val ENDERECO_COL  = "endereco"

        // below variable for our course description column.
        private const val BAIRRO_COL = "bairro"

        // below variable is for our course tracks column.
        private const val CEP_COL = "cep"

        // below variable is for our course name column
        private const val CIDADE_COL = "cidade"

        // below variable id for our course duration column.
        private const val ESTADO_COL = "estado"

        // below variable for our course description column.
        private const val TEL_COL  = "tel"

        // below variable is for our course tracks column.
        private const val CEL_COL  = "cel"
    }

    // we have created a new method for reading all the courses.
    fun readPersons(): ArrayList<PersonModel>? {
        // on below line we are creating a database for reading our database.
        val db = this.readableDatabase

        // on below line we are creating a cursor with query to read data from database.
        val cursorPersons: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        // on below line we are creating a new array list.
        val personModelArrayList: ArrayList<PersonModel> = ArrayList()

        // moving our cursor to first position.
        if (cursorPersons.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                personModelArrayList.add(
                    PersonModel(
                        cursorPersons.getString(1),
                        cursorPersons.getString(2),
                        cursorPersons.getString(3),
                        cursorPersons.getString(4),
                        cursorPersons.getString(5),
                        cursorPersons.getString(6),
                        cursorPersons.getString(7),
                        cursorPersons.getString(8)
                    )
                )
            } while (cursorPersons.moveToNext())
            // moving our cursor to next.
        }
        // at last closing our cursor and returning our array list.
        cursorPersons.close()
        return personModelArrayList
    }
}