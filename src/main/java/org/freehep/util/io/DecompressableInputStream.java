// Copyright 2001-2009, FreeHEP.
package org.freehep.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

/**
 * Special stream that can be used to read uncompressed first and compressed
 * from a certain byte.
 * 
 * @author Mark Donszelmann
 */
public class DecompressableInputStream extends DecodingInputStream {

	private boolean decompress;

	private InflaterInputStream iis;

	private InputStream in;

	/**
	 * Creates a Decompressable input stream from given stream.
	 * 
	 * @param input
	 *            stream to read from.
	 */
	public DecompressableInputStream(InputStream input) {
		super();
		in = input;
		decompress = false;
	}

	public int read() throws IOException {
		return (decompress) ? iis.read() : in.read();
	}

	public long skip(long n) throws IOException {
		return (decompress) ? iis.skip(n) : in.skip(n);
	}

	/**
	 * Start reading in compressed mode from the next byte.
	 * 
	 * @throws IOException
	 *             if read fails.
	 */
	public void startDecompressing() throws IOException {
		decompress = true;
		iis = new InflaterInputStream(in);
	}
}
