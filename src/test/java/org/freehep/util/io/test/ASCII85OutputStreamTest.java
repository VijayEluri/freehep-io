// Copyright 2001-2009, FreeHEP.
package org.freehep.util.io.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.freehep.util.io.ASCII85OutputStream;

/**
 * Test for ASCII85 Output Stream
 * 
 * @author Mark Donszelmann
 */
public class ASCII85OutputStreamTest extends AbstractStreamTest {

	/**
	 * Test method for 'org.freehep.util.io.ASCII85OutputStream.write()'
	 * 
	 * @throws Exception
	 *             if ref file cannot be found
	 */
	public void testWrite() throws Exception {
		File testFile = new File(testDir, "Quote.txt");
		File outFile = new File(outDir, "Quote.a85");
		File refFile = new File(refDir, "Quote.a85");

		ASCII85OutputStream out = new ASCII85OutputStream(new FileOutputStream(
				outFile));
		FileInputStream in = new FileInputStream(testFile);
		int b;
		while ((b = in.read()) >= 0) {
			out.write(b);
		}
		in.close();
		out.close();

		Assert.assertEquals(refFile, outFile, true);
	}

	public void testNull1() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ASCII85OutputStream out = new ASCII85OutputStream(baos);
		out.write(0);
		out.close();
		org.junit.Assert.assertEquals("!!~>", baos.toString());
	}

	public void testNull2() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ASCII85OutputStream out = new ASCII85OutputStream(baos);
		out.write(0);
		out.write(0);
		out.close();
		org.junit.Assert.assertEquals("!!!~>", baos.toString());
	}

	public void testNull3() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ASCII85OutputStream out = new ASCII85OutputStream(baos);
		out.write(0);
		out.write(0);
		out.write(0);
		out.close();
		org.junit.Assert.assertEquals("!!!!~>", baos.toString());
	}

	public void testNull4() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ASCII85OutputStream out = new ASCII85OutputStream(baos);
		out.write(0);
		out.write(0);
		out.write(0);
		out.write(0);
		out.close();
		org.junit.Assert.assertEquals("z~>", baos.toString());
	}

	public void testNull5() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ASCII85OutputStream out = new ASCII85OutputStream(baos);
		out.write(0);
		out.write(0);
		out.write(0);
		out.write(0);
		out.write(0);
		out.close();
		org.junit.Assert.assertEquals("z!!~>", baos.toString());
	}

}
