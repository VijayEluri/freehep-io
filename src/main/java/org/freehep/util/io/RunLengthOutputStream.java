// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * The RunLengthOutputStream encodes data as Run Length encoding. The exact
 * definition of Run Length encoding can be found in the PostScript Language
 * Reference (3rd ed.) chapter 3.13.3.
 * 
 * @author Mark Donszelmann
 * @version $Id: src/main/java/org/freehep/util/io/RunLengthOutputStream.java 96b41b903496 2005/11/21 19:50:18 duns $
 */
public class RunLengthOutputStream extends FilterOutputStream implements
        RunLength, FinishableOutputStream {

    private boolean end;

    private int[] buffer = new int[LENGTH];

    private int index;

    private int last;

    private int count;

    /**
     * Create a Run Length output stream
     * 
     * @param out stream to write
     */
    public RunLengthOutputStream(OutputStream out) {
        super(out);
        end = false;
        index = 0;
        last = -1;
        count = 1;
    }

    public void write(int a) throws IOException {
        a &= 0x00FF;
        if (last > 0) {
            if (a == last) {
                // counting
                writeBuffer();
                count++;
                if (count >= LENGTH) {
                    writeCount();
                }
            } else {
                // buffering
                if (count > 1) {
                    writeCount();
                } else {
                    buffer[index] = last;
                    index++;
                    if (index >= LENGTH) {
                        writeBuffer();
                    }
                }
            }
        }

        last = a;
    }

    public void finish() throws IOException {
        if (!end) {
            end = true;
            writeCount();
            writeBuffer();
            super.write(EOD);
            flush();
            if (out instanceof FinishableOutputStream) {
                ((FinishableOutputStream) out).finish();
            }
        }
    }

    public void close() throws IOException {
        finish();
        super.close();
    }

    private void writeBuffer() throws IOException {
        if (index > 0) {
            super.write(index - 1);
            for (int i = 0; i < index; i++) {
                super.write((byte) buffer[i]);
            }
        }

        index = 0;
    }

    private void writeCount() throws IOException {
        if (count > 1) {
            super.write(257 - count);
            super.write(last);
            last = -1;
        }

        count = 1;
    }
}
