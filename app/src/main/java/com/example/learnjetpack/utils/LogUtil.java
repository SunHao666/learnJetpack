package com.example.learnjetpack.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LogUtil {
	private static final boolean mIsDebug = true;
	private static String TAG = "BUGTest";
	private static LogUtil log = new LogUtil();

	private LogUtil() {
	}

	public static LogUtil getInstance() {
		return log;
	}

	public void v(Object msg) {
		if (mIsDebug) {
			String name = getFunctionName();
			String ls = (name == null ? msg.toString() : (name + " - " + msg));
			Log.v(TAG, ls);
		}
	}

	public void v(String tag, Object msg) {
		if (mIsDebug) {
			String name = getFunctionName();
			String ls = (name == null ? msg.toString() : (name + " - " + msg));
			Log.v(tag, ls);
		}
	}

	public void v(String tag, String s, Object msg) {
		if (mIsDebug) {
			String name = getFunctionName();
			String ls = (name == null ? msg.toString() : (name + " - " + s
					+ "-" + msg));
			Log.v(tag, ls);
		}
	}

	public void d(Object msg) {
		if (mIsDebug) {
			String name = getFunctionName();
			String ls = (name == null ? msg.toString() : (name + " - " + msg));
			Log.d(TAG, ls);
		}
	}

	public void d(String tag, Object msg) {
		if (mIsDebug) {
			String name = getFunctionName();
			String ls = (name == null ? msg.toString() : (name + " - " + msg));
			Log.d(tag, ls);
		}
	}

	public void d(String tag, String s, Object msg) {
		if (mIsDebug) {
			String name = getFunctionName();
			String ls = (name == null ? msg.toString() : (name + " - " + s
					+ "-" + msg));
			Log.d(tag, ls);
		}
	}

	public void i(Object msg) {
		if (mIsDebug) {
			String name = getFunctionName();
			String ls = (name == null ? msg.toString() : (name + " - " + msg));
			Log.i(TAG, ls);
		}
	}

	public void i(String tag, Object msg) {
		if (mIsDebug) {
			String name = getFunctionName();
			String ls = (name == null ? msg.toString() : (name + " - " + msg));
			Log.i(tag, ls);
		}
	}

	public void i(String tag, String s, Object msg) {
		if (mIsDebug) {
			String name = getFunctionName();
			String ls = (name == null ? msg.toString() : (name + " - " + s
					+ "-" + msg));
			Log.i(tag, ls);
		}
	}

	public void w(Object msg) {
		if (mIsDebug) {
			String name = getFunctionName();
			String ls = (name == null ? msg.toString() : (name + " - " + msg));
			Log.w(TAG, ls);
		}
	}

	public void w(String tag, Object msg) {
		if (mIsDebug) {
			String name = getFunctionName();
			String ls = (name == null ? msg.toString() : (name + " - " + msg));
			Log.w(tag, ls);
		}
	}

	public void w(String tag, String s, Object msg) {
		if (mIsDebug) {
			String name = getFunctionName();
			String ls = (name == null ? msg.toString() : (name + " - " + s
					+ "-" + msg));
			Log.w(tag, ls);
		}
	}

	public void e(Object msg) {
		if (mIsDebug) {
			String name = getFunctionName();
			String ls = (name == null ? msg.toString() : (name + " - " + msg));
			Log.e(TAG, ls);
		}
	}

	public void e(String tag, Object msg) {
		if (mIsDebug) {
			String name = getFunctionName();
			String ls = (name == null ? msg.toString() : (name + " - " + msg));
			Log.e(tag, ls);
		}
	}

	public void e(String tag, String s, Object msg) {
		if (mIsDebug) {
			String name = getFunctionName();
			String ls = (name == null ? msg.toString() : (name + " - " + s
					+ "-" + msg));
			Log.e(tag, ls);
		}
	}

	public void error(Exception ex) {
		if (mIsDebug) {
			StringBuffer sb = new StringBuffer();
			String name = this.getFunctionName();

			StackTraceElement[] sts = ex.getStackTrace();

			if (name != null) {
				sb.append(name + " - " + ex + "\r\n");
			} else {
				sb.append(ex + "\r\n");
			}

			if (sts != null && sts.length > 0) {
				for (StackTraceElement st : sts) {
					if (st != null) {
						sb.append("[ " + st.getFileName() + ":"
								+ st.getLineNumber() + " ]\r\n");
					}
				}
			}
			Log.e(TAG, sb.toString());
		}
	}

	private String getFunctionName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();

		if (sts == null) {
			return null;
		}

		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}

			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}

			if (st.getClassName().equals(getClass().getName())) {
				continue;
			}

			return "[" + Thread.currentThread().getId() + ": "
					+ st.getFileName() + ":" + st.getLineNumber() + "]";
		}

		return null;
	}

	public  String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}
	/**
	 * 截断输出日志
	 * @param msg
	 */
	public static void longlog(String tag, String msg) {
		if (tag == null || tag.length() == 0
				|| msg == null || msg.length() == 0)
			return;

		int segmentSize = 3 * 1024;
		long length = msg.length();
		// 长度小于等于限制直接打印
		if (length <= segmentSize ) {
			Log.e(tag, msg);
		}else {
			// 循环分段打印日志
			while (msg.length() > segmentSize ) {
				String logContent = msg.substring(0, segmentSize );
				msg = msg.replace(logContent, "");
				Log.e(tag, logContent);
			}
			// 打印剩余日志
			Log.e(tag, msg);
		}
	}
}
