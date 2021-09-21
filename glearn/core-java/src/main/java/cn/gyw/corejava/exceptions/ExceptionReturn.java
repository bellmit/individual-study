package cn.gyw.corejava.exceptions;

/**
 * finally 返回值
 */
public class ExceptionReturn {

    private int index = 129;

    /**
     * try 中return 的结果会暂存在r 中，finally 中返回值，会修改 r
     * @param args
     */
    public static void main(String[] args) {
        ExceptionReturn exceptionReturn = new ExceptionReturn();
        System.out.println(exceptionReturn.getIndex());
        System.out.println(exceptionReturn.getObject());
    }

    @SuppressWarnings("finally")
	public int getIndex() {
        try {
            add();
            return index;
        } catch (Exception e) {

        } finally {
            System.out.println("finally >>" + index);
            div();
            System.out.println("finally <<" + index);
            return index;
        }
    }
    
    @SuppressWarnings("finally")
    public Result getObject() {
    	Result result = null;
    	try {
    		result = new Result();
    		result.setMessage("success");
    		return result;
    	} finally {
    		result = new Result();
    		result.setMessage("error");
    		return result;
    	}
    }

    public void add() {
        index++;
    }

    public void div() {
        index--;
    }
    
    class Result {
    	String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return "Result [message=" + message + "]";
		}
    }
}
