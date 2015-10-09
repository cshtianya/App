package com.tnw;

public class OrderState {
	public static final int CANCEL = -1;
	public static final int WAIT_PAY = 0;
	public static final int PAID = 1;
	public static final int DELIVERED = 2;
	public static final int COMPLETED = 3;
	public static final int APPLICATION_RETURN = 4;
	public static final int RETURN = 5;
	public static final int REFUSE_RETURN = 6;
	public static final int AGREE_RETURN = 7;
}
