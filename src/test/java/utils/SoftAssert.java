package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

import java.util.List;
import java.util.Map;



public class SoftAssert extends org.testng.asserts.SoftAssert {
	private final Map<AssertionError, IAssert> m_errors = Maps.newHashMap();
	// private static UISoftAssert objSoftAssert;
	private ExtentTest test;

	public SoftAssert(ExtentTest test) {
		this.test = test;
	}

	protected void doAssert(IAssert assertCommand) {
		onBeforeAssert(assertCommand);
		try {
			executeAssert(assertCommand);
			onAssertSuccess(assertCommand);
		} catch (AssertionError ex) {
			m_errors.put(ex, assertCommand);
			onAssertFailure(assertCommand, ex);
			// throw ex;
		} finally {
			onAfterAssert(assertCommand);
		}
	}

	@Override
	public void executeAssert(IAssert a) {
		try {
			a.doAssert();
		} catch (AssertionError ex) {
			m_errors.put(ex, a);
			// onAssertFailure(a, ex);
			throw ex;
		}
	}

	@Override
	public void assertAll() {
		if (!m_errors.isEmpty()) {
			StringBuilder sb = new StringBuilder("The following asserts failed:\n");
			boolean first = true;
			for (Map.Entry<AssertionError, IAssert> ae : m_errors.entrySet()) {
				if (first) {
					sb.append("----------------\n");
					first = false;
				} else {
					sb.append(", ");
				}
				sb.append(ae.getValue().getMessage());
				sb.append("\n");
				sb.append(ae.getKey().getMessage());
				sb.append("\n");
				for (StackTraceElement st : ae.getKey().getStackTrace())
				{
					sb.append(st.toString());
					sb.append("\n");
				}
			}
			throw new AssertionError(sb.toString());
		}
	}

	@Override
	public void onAfterAssert(IAssert a) {
		// super.onAfterAssert(a);
	}

	@Override
	public void onBeforeAssert(IAssert a) {
		// Reporter.log("");
		// Reporter.log("ASSERT DESCRIPTION: " + a.getMessage());

	}

	@Override
	public void onAssertFailure(IAssert a, AssertionError ex) {
		try {
			// System.out.println(ex instanceof(JSONException));
			test.log(Status.FAIL, "<pre><b><font color='red'>" + "Functional Failure :: " + ex.getMessage() + "."
					+ "</font></b></pre>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onAssertSuccess(IAssert a) {
		try {
			test.log(Status.PASS, "<b><font color='green'>" + a.getMessage() + "</font></b>");
		} catch (Exception e) {

		}
	}


	




	
	}


