package com.nflabs.zeppelin.zengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hive.conf.HiveConf;

import com.nflabs.zeppelin.conf.ZeppelinConfiguration;
import com.nflabs.zeppelin.conf.ZeppelinConfiguration.ConfVars;
import com.nflabs.zeppelin.zengine.Z;
import com.nflabs.zeppelin.zengine.ZException;
import com.nflabs.zeppelin.zengine.ZQL;
import com.nflabs.zeppelin.zengine.ZQLException;

import junit.framework.TestCase;

public class ZQLTest extends TestCase {
	private File tmpDir;
												
	protected void setUp() throws Exception {
		tmpDir = new File(System.getProperty("java.io.tmpdir")+"/ZeppelinLTest_"+System.currentTimeMillis());
		tmpDir.mkdir();
		
		new File(tmpDir.getAbsolutePath()+"/test").mkdir();
		File erb = new File(tmpDir.getAbsolutePath()+"/test/test.erb");
		FileOutputStream out = new FileOutputStream(erb);		
		out.write(("CREATE VIEW <%= z."+Q.OUTPUT_VAR_NAME+" %> AS select * from (<%= z."+Q.INPUT_VAR_NAME+" %>) a limit <%= z.param('limit') %>\n").getBytes());
		out.close();
	
		// create resource
		FileOutputStream resource = new FileOutputStream(new File(tmpDir.getAbsolutePath()+"/test/test_data.log"));
		resource.write("".getBytes());
		resource.close();
		
		System.out.println(tmpDir.toURI().toString());
		System.setProperty(ConfVars.ZEPPELIN_ZAN_LOCAL_REPO.getVarName(), tmpDir.toURI().toString());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		delete(tmpDir);
	}
	
	private void delete(File file){
		if(file.isFile()) file.delete();
		else if(file.isDirectory()){
			File [] files = file.listFiles();
			if(files!=null && files.length>0){
				for(File f : files){
					delete(f);
				}
			}
			file.delete();
		}
	}
	
	
	public void testPipe() throws ZException, ZQLException {
		ZQL zql = new ZQL();
		zql.append("select * from bank | select * from <%= z."+Q.INPUT_VAR_NAME+" %> limit 10");
		List<Z> z = zql.compile();
		
		assertEquals(1, z.size());
		assertEquals("select * from "+z.get(0).prev().name()+" limit 10", z.get(0).getQuery());
		z.get(0).release();
	}
	
	
	public void testSemicolon() throws ZException, ZQLException{
		ZQL zql = new ZQL();
		zql.append("select * from bank | select * from <%= z."+Q.INPUT_VAR_NAME+" %> limit 10; show tables");
		List<Z> z = zql.compile();
		
		assertEquals(2, z.size());
		assertEquals("select * from "+z.get(0).prev().name()+" limit 10", z.get(0).getQuery());
		assertEquals("show tables", z.get(1).getQuery());
		z.get(0).release();
	}
	
	public void testRedirection() throws ZException, ZQLException{
		ZQL zql = new ZQL();
		zql.append("select * from bank limit 10 > summary");
		List<Z> z = zql.compile();
		
		assertEquals(1, z.size());
		assertEquals("select * from bank limit 10", z.get(0).getQuery());
		z.get(0).release();
		
		
	}

	public void testLstmtSimple() throws ZException, ZQLException{
		ZQL zql = new ZQL("test");
		List<Z> zList = zql.compile();
		assertEquals(1, zList.size());
		Z z = zList.get(0);
		assertEquals("CREATE VIEW "+z.name()+" AS select * from () a limit ", z.getQuery());
		z.release();
	}
	
	public void testLstmtParam() throws ZException, ZQLException{
		ZQL zql = new ZQL("test(limit=10)");
		Z z = zql.compile().get(0);
		assertEquals("CREATE VIEW "+z.name()+" AS select * from () a limit 10", z.getQuery());
	}
	
	public void testLstmtArg() throws IOException, ZException, ZQLException{
		ZQL zql = new ZQL("select * from test | test(limit=10)");
		
		List<Z> z = zql.compile();
		assertEquals(1, z.size());
		assertEquals("CREATE VIEW "+z.get(0).name()+" AS select * from ("+z.get(0).prev().name()+") a limit 10", z.get(0).getQuery());
	}

}
