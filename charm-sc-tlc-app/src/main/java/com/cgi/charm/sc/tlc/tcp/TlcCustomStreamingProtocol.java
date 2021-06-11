package com.cgi.charm.sc.tlc.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.mule.transport.tcp.protocols.DirectProtocol;

/**
 * @author Naveen Chintala
 *
 */
public class TlcCustomStreamingProtocol extends DirectProtocol {

	@Override
	public Object read(InputStream is) throws IOException {
		String port = TlcTcpClientRegistry.getInstance().getCurrentPort();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bufferSize);
	   	int limit = -1;
		boolean repeat;
		try {
			byte[] buffer = new byte[bufferSize];
			int len;
			int remain = remaining(limit, limit, 0);

			do {

				len = copy(is, buffer, baos, remain);
				remain = remaining(limit, remain, len);
				repeat = EOF != len && remain > 0
						&& isRepeat(len, is.available());

			} while (repeat);
		} finally {
			baos.flush();
			baos.close();
		}
		if (0 == baos.toByteArray().length) {
			return "";
		}
		byte[] output = nullEmptyArray(baos.toByteArray());

		//REMARK: are you sure you meant to implicitly call byte[].toString here?
		// Did you perhaps mean to call new String(byte[]) here?
		// Since this will lead to the string being something like
		// <port>_byte[]@... instead of the actual String value of the byte[]
		String finalOutput = port + "_" + (new String(output));
		return finalOutput;
	}

	@Override
	public void write(OutputStream os, Object data) throws IOException {
		super.write(os, data);
	}

}
