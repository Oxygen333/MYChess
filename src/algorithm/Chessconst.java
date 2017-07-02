package algorithm;

/**
 * �������ӵĳ���ֵ,�����ֹ涨
 */
public class Chessconst {

	public static final int NOCHESS = 0;

	public static final int B_KING = 1;
	public static final int B_CAR = 2;
	public static final int B_HORSE = 3;
	public static final int B_CANON = 4;
	public static final int B_BISHOP = 5;
	public static final int B_ELEPHANT = 6;
	public static final int B_PAWN = 7;

	public static final int R_KING = 8;
	public static final int R_CAR = 9;
	public static final int R_HORSE = 10;
	public static final int R_CANON = 11;
	public static final int R_BISHOP = 12;
	public static final int R_ELEPHANT = 13;
	public static final int R_PAWN = 14;

	public static String getChessText(int chessId) {
		switch (chessId) {
			case 2:
				return "杌�";
			case 3:
				return "棣�";
			case 6:
				return "璞�";
			case 5:
				return "澹�";
			case 1:
				return "灏�";
			case 7:
				return "鍗�";
			case 4:
				return "鐐�";
			case 11:
				return "鐐�";
			case 9:
				return "杞�";
			case 10:
				return "棣�";
			case 13:
				return "鐩�";
			case 12:
				return "浠�";
			case 8:
				return "甯�";
			case 14:
				return "鍏�";	
			default:
				break;
		}
		return "";
	}
	
}
