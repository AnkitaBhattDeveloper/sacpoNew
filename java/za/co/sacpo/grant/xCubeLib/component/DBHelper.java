package za.co.sacpo.grant.xCubeLib.component;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "olumsPPortal.db";

	public static final String JOB_TABLE_NAME = "tbl_jobs";
	public static final String JOB_COLUMN_ID = "j_d_id";
	public static final String JOB_COLUMN_TITLE = "j_d_job_title";
	public static final String JOB_COLUMN_KEYWORD = "j_d_keywords";
	public static final String JOB_COLUMN_DESC = "j_d_job_description";
	public static final String JOB_COLUMN_APPLIED = "j_r_applied_date";
	public static final String JOB_COLUMN_OFO = "o_j_ofo_id";
	public static final String JOB_COLUMN_CITY = "c_name";
	public static final String JOB_COLUMN_PROVINCE = "p_name";
	public static final String JOB_COLUMN_EXP = "expired";
	public static final String JOB_COLUMN_POST = "posted";
	public static final String JOB_COLUMN_e_b_id = "e_b_id";
	public static final String JOB_COLUMN_LINKJID = "link_j_d_id";

	public static final String USER_TABLE_NAME = "tbl_user";
	public static final String USER_COLUMN_ID = "u_id";
	public static final String USER_COLUMN_FNAME = "u_p_fname";
	public static final String USER_COLUMN_SURNAME = "u_p_surname";
	public static final String USER_COLUMN_EMAIL = "u_email";
	public static final String USER_COLUMN_THUMB_IMAGE = "u_p_image_thumb";
	public static final String USER_COLUMN_SIM = "u_sim";
	public static final String USER_COLUMN_I_TYPE = "u_i_type";
	public static final String USER_COLUMN_UNIVERSITY = "u_un_id";
	public static final String USER_COLUMN_COLLEGE = "u_college_id";
	public static final String USER_COLUMN_SYNCTIME = "u_sync_time";
	public static final String USER_COLUMN_LINKUID = "link_u_id";

	public static final String EMP_TABLE_NAME = "tbl_employers";
	public static final String EMP_COLUMN_ID = "e_id";
	public static final String EMP_COLUMN_NAME = "e_name";
	public static final String EMP_COLUMN_WEBSITE = "e_website";
	public static final String EMP_COLUMN_BRANCH = "e_b_branch_name";
	public static final String EMP_COLUMN_BUILDING = "e_b_building_name";
	public static final String EMP_COLUMN_LAT = "e_b_latitude";
	public static final String EMP_COLUMN_LONG = "e_b_longitude";
	public static final String EMP_COLUMN_LINKEBID = "link_e_b_id";
	public static final String EMP_COLUMN_LINKEID = "link_e_id";

	public static final String OFO_TABLE_NAME = "tbl_ofo";
	public static final String OFO_COLUMN_ID = "o_o_id";
	public static final String OFO_COLUMN_COUNTS = "cnt";
	public static final String OFO_COLUMN_OLINKID = "link_o_o_id";
	public static final String OFO_COLUMN_TITLE = "o_o_description";

	private HashMap hp;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table tbl_user "
				+ "(u_id integer primary key, u_p_fname text,u_p_surname text,u_email text, u_p_image_thumb text,u_sim text,u_i_type integer,u_un_id integer,u_college_id integer,u_sync_time text,link_u_id integer)");
		db.execSQL("create table tbl_jobs "
				+ "(j_d_id integer primary key, j_d_job_title text,j_d_keywords text,j_d_job_description text, j_r_applied_date text,o_j_ofo_id integer,c_name text,p_name text, expired text,posted text,link_j_d_id integer,e_b_id integer)");
		db.execSQL("create table tbl_employers "
				+ "(u_id integer primary key, e_name text,e_website text,e_b_branch_name text, e_b_building_name text,e_b_latitude text,e_b_longitude text,link_e_b_id integer,link_e_id integer)");
		db.execSQL("create table tbl_ofo "
				+ "(o_o_id integer primary key, o_o_description text,cnt integer,link_o_o_id integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS tbl_user");
		db.execSQL("DROP TABLE IF EXISTS tbl_jobs");
		db.execSQL("DROP TABLE IF EXISTS tbl_employers");
		db.execSQL("DROP TABLE IF EXISTS tbl_ofo");
		onCreate(db);
	}

	public void createTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("create table tbl_user "
				+ "(u_id integer primary key, u_p_fname text,u_p_surname text,u_email text, u_p_image_thumb text,u_sim text,u_i_type integer,u_un_id integer,u_college_id integer,u_sync_time text,link_u_id integer)");
		db.execSQL("create table tbl_jobs "
				+ "(j_d_id integer primary key, j_d_job_title text,j_d_keywords text,j_d_job_description text, j_r_applied_date text,o_j_ofo_id integer,c_name text,p_name text, expired text,posted text,link_j_d_id integer)");
		db.execSQL("create table tbl_employers "
				+ "(u_id integer primary key, e_name text,e_website text,e_b_branch_name text, e_b_building_name text,e_b_latitude text,e_b_longitude text,link_e_b_id integer,link_e_id integer)");
		db.execSQL("create table tbl_ofo "
				+ "(o_o_id integer primary key, o_o_description text,cnt integer,link_o_o_id integer)");
	}

	public void dropTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS tbl_user");
		db.execSQL("DROP TABLE IF EXISTS tbl_jobs");
		db.execSQL("DROP TABLE IF EXISTS tbl_employers");
		db.execSQL("DROP TABLE IF EXISTS tbl_ofo");
		// onCreate(db);
	}

	public void emptyJDataTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from tbl_jobs");
	}

	public void emptyODataTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from tbl_ofo");
	}

	public void emptyEDataTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from tbl_employers");
	}

	public boolean insertOFO(String o_o_description, int cnt, int link_o_o_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("o_o_description", o_o_description);
		contentValues.put("cnt", cnt);
		contentValues.put("link_o_o_id", link_o_o_id);
		db.insert("tbl_ofo", null, contentValues);
		return true;
	}

	public Cursor geOFOData(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from tbl_ofo where o_o_id=" + id	+ "", null);
		return res;
	}

	public Cursor getOFODataBySim(int sim) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from o_o_id where link_o_o_id="	+ sim + "", null);
		return res;
	}

	public int numberOfOFO() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, OFO_TABLE_NAME);
		return numRows;
	}

	public boolean updateOFOBySim(String o_o_description, int cnt,int link_o_o_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("o_o_description", o_o_description);
		contentValues.put("link_o_o_id", link_o_o_id);
		contentValues.put("cnt", cnt);
		db.update("tbl_ofo", contentValues, "link_o_o_id = ? ",	new String[] { Integer.toString(link_o_o_id) });
		return true;
	}

	public boolean updateEmployer(Integer id, String o_o_description, int cnt,	int link_o_o_id) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("o_o_description", o_o_description);
		contentValues.put("link_o_o_id", link_o_o_id);
		contentValues.put("cnt", cnt);
		db.update("tbl_ofo", contentValues, "o_o_id = ? ",	new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deleteOFO(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete("tbl_ofo", "o_o_id = ? ",new String[] { Integer.toString(id) });
	}

	public boolean insertEmployers(String e_name, String e_website,	String e_b_branch_name, String e_b_building_name,String e_b_latitude, String e_b_longitude, int link_e_b_id,	int link_e_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("e_name", e_name);
		contentValues.put("e_website", e_website);
		contentValues.put("e_b_branch_name", e_b_branch_name);
		contentValues.put("e_b_building_name", e_b_building_name);
		contentValues.put("e_b_latitude", e_b_latitude);
		contentValues.put("e_b_longitude", e_b_longitude);
		contentValues.put("link_e_b_id", link_e_b_id);
		contentValues.put("link_e_id", link_e_id);
		db.insert("tbl_employers", null, contentValues);
		return true;
	}

	public Cursor getEmployerData(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from tbl_employers where e_id=" + id
				+ "", null);
		return res;
	}

	public Cursor getEmployerDataBySim(int sim) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from tbl_employers where link_e_b_id=" + sim + "",null);
		return res;
	}

	public int numberOfEmployers() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, EMP_TABLE_NAME);
		return numRows;
	}

	public boolean updateEmployerBySim(String e_name, String e_website,String e_b_branch_name, String e_b_building_name,String e_b_latitude, String e_b_longitude, int link_e_b_id,	int link_e_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("e_name", e_name);
		contentValues.put("e_website", e_website);
		contentValues.put("e_b_branch_name", e_b_branch_name);
		contentValues.put("e_b_building_name", e_b_building_name);
		contentValues.put("e_b_latitude", e_b_latitude);
		contentValues.put("e_b_longitude", e_b_longitude);
		contentValues.put("link_e_id", link_e_id);
		db.update("tbl_employers", contentValues, "link_e_b_id = ? ",new String[] { Integer.toString(link_e_b_id) });
		return true;
	}

	public boolean updateEmployer(Integer id, String e_name, String e_website,String e_b_branch_name, String e_b_building_name,	String e_b_latitude, String e_b_longitude, int link_e_b_id,	int link_e_id) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("e_name", e_name);
		contentValues.put("e_website", e_website);
		contentValues.put("e_b_branch_name", e_b_branch_name);
		contentValues.put("e_b_building_name", e_b_building_name);
		contentValues.put("e_b_latitude", e_b_latitude);
		contentValues.put("e_b_longitude", e_b_longitude);
		contentValues.put("link_e_b_id", link_e_b_id);
		contentValues.put("link_e_id", link_e_id);
		db.update("tbl_employers", contentValues, "e_id = ? ",	new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deleteEmployer(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete("tbl_employers", "e_id = ? ",new String[] { Integer.toString(id) });
	}

	public boolean insertJobs(String title, String keywords, String a_date,	String desc, int ofo, String city, String pro, String exp,String post, int e_b_id, int link_j_d_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("j_d_job_title", title);
		contentValues.put("j_d_keywords", keywords);
		contentValues.put("j_r_applied_date", a_date);
		contentValues.put("j_d_job_description", desc);
		contentValues.put("o_j_ofo_id", ofo);
		contentValues.put("c_name", city);
		contentValues.put("p_name", pro);
		contentValues.put("expired", exp);
		contentValues.put("posted", post);
		contentValues.put("e_b_id", e_b_id);
		contentValues.put("link_j_d_id", link_j_d_id);
		db.insert("tbl_jobs", null, contentValues);
		return true;
	}

	public Cursor getJobData(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from tbl_jobs where j_d_id=" + id + "", null);
		return res;
	}

	public Cursor getAllJobData() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from tbl_jobs ", null);
		return res;
	}

	public Cursor getJobDataBySim(int sim) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from tbl_jobs where link_j_d_id=" + sim + "", null);
		return res;
	}

	public int numberOfJobs() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, JOB_TABLE_NAME);
		return numRows;
	}

	public boolean updateJobBySim(String title, String keywords, String a_date,	String desc, int ofo, String city, String pro, String exp,String post, String e_b_id, int link_j_d_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("j_d_job_title", title);
		contentValues.put("j_d_keywords", keywords);
		contentValues.put("j_r_applied_date", a_date);
		contentValues.put("j_d_job_description", desc);
		contentValues.put("o_j_ofo_id", ofo);
		contentValues.put("c_name", city);
		contentValues.put("p_name", pro);
		contentValues.put("expired", exp);
		contentValues.put("posted", post);
		contentValues.put("e_b_id", e_b_id);
		db.update("tbl_jobs", contentValues, "link_j_d_id = ? ",new String[] { Integer.toString(link_j_d_id) });
		return true;
	}

	public boolean updateJob(Integer id, String title, String keywords,	String a_date, String desc, int ofo, String city, String pro,String exp, String post, int e_b_id, int link_j_d_id) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("j_d_job_title", title);
		contentValues.put("j_d_keywords", keywords);
		contentValues.put("j_r_applied_date", a_date);
		contentValues.put("j_d_job_description", desc);
		contentValues.put("o_j_ofo_id", ofo);
		contentValues.put("c_name", city);
		contentValues.put("p_name", pro);
		contentValues.put("expired", exp);
		contentValues.put("posted", post);
		contentValues.put("e_b_id", e_b_id);
		contentValues.put("link_j_d_id", link_j_d_id);
		db.update("tbl_jobs", contentValues, "j_d_id = ? ",	new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deleteJob(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete("tbl_jobs", "j_d_id = ? ",	new String[] { Integer.toString(id) });
	}

	public boolean insertUser(String fname, String sname, String email,	String timg, String sim, int type, int uni, int col, int luid) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("u_p_fname", fname);
		contentValues.put("u_p_surname", sname);
		contentValues.put("u_email", email);
		contentValues.put("u_p_image_thumb", timg);
		contentValues.put("u_sim", sim);
		contentValues.put("u_i_type", type);
		contentValues.put("u_un_id", uni);
		contentValues.put("u_college_id", col);
		contentValues.put("link_u_id", luid);
		db.insert("tbl_user", null, contentValues);
		return true;
	}

	public Cursor getUserData(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from tbl_user where u_id=" + id + "", null);
		return res;
	}

	public Cursor getUserDataBySim(String sim) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from tbl_user where u_sim=" + sim + "", null);
		return res;
	}

	public int numberOfRows() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, USER_TABLE_NAME);
		return numRows;
	}

	public boolean updateUserBySim(String u_sim, String fname, String sname,String email, String timg, int type, int uni, int col, int luid) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("u_p_fname", fname);
		contentValues.put("u_p_surname", sname);
		contentValues.put("u_email", email);
		contentValues.put("u_p_image_thumb", timg);
		contentValues.put("link_u_id", luid);
		contentValues.put("u_i_type", type);
		contentValues.put("u_un_id", uni);
		contentValues.put("u_college_id", col);
		db.update("tbl_user", contentValues, "u_sim = ? ",new String[] { u_sim });
		return true;
	}

	public boolean updateUser(Integer id, String fname, String sname,String email, String timg, String sim, int type, int uni, int col,int luid) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("u_p_fname", fname);
		contentValues.put("u_p_surname", sname);
		contentValues.put("u_email", email);
		contentValues.put("u_p_image_thumb", timg);
		contentValues.put("u_sim", sim);
		contentValues.put("u_i_type", type);
		contentValues.put("u_un_id", uni);
		contentValues.put("u_college_id", col);
		contentValues.put("link_u_id", luid);
		db.update("tbl_user", contentValues, "u_id = ? ",new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deleteUser(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete("tbl_user", "u_id = ? ",new String[] { Integer.toString(id) });
	}

	public ArrayList<String> getAllCotacts() {
		ArrayList<String> array_list = new ArrayList<String>();

		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from tbl_user", null);
		res.moveToFirst();

		while (res.isAfterLast() == false) {
			array_list.add(res.getString(res.getColumnIndex(USER_COLUMN_FNAME))	+ " "+ res.getString(res.getColumnIndex(USER_COLUMN_SURNAME)));
			res.moveToNext();
		}
		return array_list;
	}
}