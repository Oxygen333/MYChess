package algorithm;

public class Evaluation {
	
	// ����ÿ�����ӵĻ�����ֵ
	final static int BASEVALUE_PAWN = 100;
	final static int BASEVALUE_BISHOP = 250;
	final static int BASEVALUE_ELEPHANT = 250;
	final static int BASEVALUE_CAR = 500;
	final static int BASEVALUE_HORSE = 350;
	final static int BASEVALUE_CANON = 350;
	final static int BASEVALUE_KING = 10000;
	
	// ��������ӵ������ ,ÿ��һ������λ��Ӧ���ϵķ�ֵ
	final static int FLEXIBILITY_PAWN = 15;
	final static int FLEXIBILITY_BISHOP = 1;
	final static int FLEXIBILITY_ELEPHANT = 1;
	final static int FLEXIBILITY_CAR = 6;
	final static int FLEXIBILITY_HORSE = 12;
	final static int FLEXIBILITY_CANON = 6;
	final static int FLEXIBILITY_KING = 0;
	
	//����ĸ���ֵ����
	final int BA0[][]= new int[][]
	{
		{0,  0,  0,  0,  0,  0,  0,  0,  0},
		{90,90,110,120,120,120,110,90,90},
		{90,90,110,120,120,120,110,90,90},
		{70,90,110,110,110,110,110,90,70},
		{70,70,70, 70, 70,  70, 70,70,70},
		{0,  0,  0,  0,  0,  0,  0,  0,  0},
		{0,  0,  0,  0,  0,  0,  0,  0,  0},
		{0,  0,  0,  0,  0,  0,  0,  0,  0},
		{0,  0,  0,  0,  0,  0,  0,  0,  0},
		{0,  0,  0,  0,  0,  0,  0,  0,  0},
	};
	
	//�ڱ��ĸ���ֵ����
	final int[][] BA1 = new int[][]
	{
		{0,  0,  0,  0,  0,  0,  0,  0,  0},
		{0,  0,  0,  0,  0,  0,  0,  0,  0},
		{0,  0,  0,  0,  0,  0,  0,  0,  0},
		{0,  0,  0,  0,  0,  0,  0,  0,  0},
		{0,  0,  0,  0,  0,  0,  0,  0,  0},
		{70,70,70, 70, 70,70, 70,70, 70},
		{70,90,110,110,110,110,110,90,70},
		{90,90,110,120,120,120,110,90,90},
		{90,90,110,120,120,120,110,90,90},
		{0,  0, 0,  0,  0,  0,  0,  0,  0},
	};
	
	/**
	 * Ϊÿһ�������ظ���ֵ
	 * @param x
	 * @param y
	 * @param CurSituation
	 * @return
	 */
	int GetBingValue(int x, int y, int CurSituation[][])
	{
		if (CurSituation[y][x] == Chessconst.R_PAWN)
			return BA0[y][x];
		
		if (CurSituation[y][x] == Chessconst.B_PAWN)
			return BA1[y][x];
		return 0;
	}
	
	int m_BaseValue[]= new int[15];//������ӻ�����ֵ������
	int m_FlexValue[]= new int[15];//�����������Է���������
	int m_AttackPos[][]= new int[10][9];//���ÿһλ�ñ���в����Ϣ
	int m_GuardPos[][]= new int[10][9];//���ÿһλ�ñ���������Ϣ
	int m_FlexibilityPos[][]= new int[10][9];//���ÿһλ���ϵ����ӵ�����Է���
	int m_chessValue[][] =new int[10][9];//���ÿһλ���ϵ����ӵ��ܼ�ֵ
	int nPosCount = 0;//��¼һ���ӵ����λ�ø���
	Chessmanpos RelatePos[]= new Chessmanpos[20];//��¼һ�������λ�ӵ�����
	
	public Evaluation() {
		m_BaseValue[Chessconst.B_KING] = BASEVALUE_KING; 
		m_BaseValue[Chessconst.B_CAR] = BASEVALUE_CAR; 
		m_BaseValue[Chessconst.B_HORSE] = BASEVALUE_HORSE; 
		m_BaseValue[Chessconst.B_BISHOP] = BASEVALUE_BISHOP; 
		m_BaseValue[Chessconst.B_ELEPHANT] = BASEVALUE_ELEPHANT; 
		m_BaseValue[Chessconst.B_CANON] = BASEVALUE_CANON; 
		m_BaseValue[Chessconst.B_PAWN] = BASEVALUE_PAWN; 

		m_BaseValue[Chessconst.R_KING] = BASEVALUE_KING; 
		m_BaseValue[Chessconst.R_CAR] = BASEVALUE_CAR; 
		m_BaseValue[Chessconst.R_HORSE] = BASEVALUE_HORSE; 
		m_BaseValue[Chessconst.R_BISHOP] = BASEVALUE_BISHOP; 
		m_BaseValue[Chessconst.R_ELEPHANT] = BASEVALUE_ELEPHANT; 
		m_BaseValue[Chessconst.R_CANON] = BASEVALUE_CANON; 
		m_BaseValue[Chessconst.R_PAWN] = BASEVALUE_PAWN; 

		m_FlexValue[Chessconst.B_KING] = FLEXIBILITY_KING; 
		m_FlexValue[Chessconst.B_CAR] = FLEXIBILITY_CAR; 
		m_FlexValue[Chessconst.B_HORSE] = FLEXIBILITY_HORSE; 
		m_FlexValue[Chessconst.B_BISHOP] = FLEXIBILITY_BISHOP; 
		m_FlexValue[Chessconst.B_ELEPHANT] = FLEXIBILITY_ELEPHANT; 
		m_FlexValue[Chessconst.B_CANON] = FLEXIBILITY_CANON; 
		m_FlexValue[Chessconst.B_PAWN] = FLEXIBILITY_PAWN; 

		m_FlexValue[Chessconst.R_KING] = FLEXIBILITY_KING; 
		m_FlexValue[Chessconst.R_CAR] = FLEXIBILITY_CAR; 
		m_FlexValue[Chessconst.R_HORSE] = FLEXIBILITY_HORSE; 
		m_FlexValue[Chessconst.R_BISHOP] = FLEXIBILITY_BISHOP; 
		m_FlexValue[Chessconst.R_ELEPHANT] = FLEXIBILITY_ELEPHANT; 
		m_FlexValue[Chessconst.R_CANON] = FLEXIBILITY_CANON; 
		m_FlexValue[Chessconst.R_PAWN] = FLEXIBILITY_PAWN; 
	}
	
	public int count = 0 ;//ȫ�ֱ���
	/**
	 * 
	 * @param position
	 * @param bIsRedTurn �ֵ�˭�ı�־��!0 �Ǻ�,0 �Ǻ�
	 * @return
	 */
	public int Eveluate(int position[][], int bIsRedTurn){
		int i, j, k;
		int nChessType, nTargetType;
		count ++;

		for(i=0;i<10;i++){ // ��ʼ����ֵ
			for(j=0;j<9;j++){
				m_chessValue[i][j] = 0 ;
				m_AttackPos[i][j] = 0 ;
				m_GuardPos[i][j] = 0 ;
				m_FlexibilityPos[i][j] = 0;
			}
		}
		
		for(i = 0; i < 10; i++)
			for(j = 0; j < 9; j++)
			{
				if(position[i][j] != Chessconst.NOCHESS)
				{
					nChessType = position[i][j];
					GetRelatePiece(position, j, i);
					for (k = 0; k < nPosCount; k++)
					{
						nTargetType = position[RelatePos[k].y][RelatePos[k].x];
						if (nTargetType == Chessconst.NOCHESS)
						{
							m_FlexibilityPos[i][j]++;	
						}
						else
						{
							if (Chessutil.isSameSide(nChessType, nTargetType))
							{
								m_GuardPos[RelatePos[k].y][RelatePos[k].x]++;
							}else
							{
								m_AttackPos[RelatePos[k].y][RelatePos[k].x]++;
								m_FlexibilityPos[i][j]++;	
								switch (nTargetType)
								{
								case Chessconst.R_KING:
									if (bIsRedTurn == 0)
										return 18888;
									break;
								case Chessconst.B_KING:
									if (bIsRedTurn == 1)
										return 18888;
									break;
								default:
									m_AttackPos[RelatePos[k].y][RelatePos[k].x] += (30 + (m_BaseValue[nTargetType] - m_BaseValue[nChessType])/10)/10;
									break;
								}
							}
						}
					}
				}
			}

		for(i = 0; i < 10; i++)
			for(j = 0; j < 9; j++)
			{
				if(position[i][j] != Chessconst.NOCHESS)
				{
					nChessType = position[i][j];
					m_chessValue[i][j]++;
					m_chessValue[i][j] += m_FlexValue[nChessType] * m_FlexibilityPos[i][j];
					m_chessValue[i][j] += GetBingValue(j, i, position);
				}
			}
		int nHalfvalue;
		for(i = 0; i < 10; i++)
			for(j = 0; j < 9; j++)
			{
				if(position[i][j] != Chessconst.NOCHESS)
				{
					nChessType = position[i][j];
					nHalfvalue = m_BaseValue[nChessType]/16;
					m_chessValue[i][j] += m_BaseValue[nChessType];
					
					if (Chessutil.isRed(nChessType))
					{
						if (m_AttackPos[i][j] != 0)
						{
							if (bIsRedTurn != 0 )
							{
								if (nChessType == Chessconst.R_KING)
								{
									m_chessValue[i][j]-= 20;
								}
								else
								{
									m_chessValue[i][j] -= nHalfvalue * 2;
									if (m_GuardPos[i][j] != 0)
										m_chessValue[i][j] += nHalfvalue;
								}
							}
							else
							{
								if (nChessType == Chessconst.R_KING)
									return 18888;
								m_chessValue[i][j] -= nHalfvalue*10;
								if (m_GuardPos[i][j] != 0)
									m_chessValue[i][j] += nHalfvalue*9;
							}
							m_chessValue[i][j] -= m_AttackPos[i][j];
						}
						else
						{
							if (m_GuardPos[i][j] != 0)
								m_chessValue[i][j] += 5;
						}
					}
					else
					{
						if (m_AttackPos[i][j] != 0)
						{
							if (bIsRedTurn == 0)
							{
								if (nChessType == Chessconst.B_KING)									
								{
									m_chessValue[i][j]-= 20;
								}
								else
								{
									m_chessValue[i][j] -= nHalfvalue * 2;
									if (m_GuardPos[i][j] != 0)
										m_chessValue[i][j] += nHalfvalue;
								}
							}
							else
							{
								if (nChessType == Chessconst.B_KING)
									return 18888;
								m_chessValue[i][j] -= nHalfvalue*10;
								if (m_GuardPos[i][j] != 0)
									m_chessValue[i][j] += nHalfvalue*9;
							}
							m_chessValue[i][j] -= m_AttackPos[i][j];
						}
						else
						{
							if (m_GuardPos[i][j] != 0)
								m_chessValue[i][j] += 5;
						}
					}
				}
			}

		int nRedValue = 0; 
		int	nBlackValue = 0;

		for(i = 0; i < 10; i++)
			for(j = 0; j < 9; j++)
			{
				nChessType = position[i][j];
//				if (nChessType == R_KING || nChessType == B_KING)
//					m_chessValue[i][j] = 10000;	
				if (nChessType != Chessconst.NOCHESS)
				{
					if (Chessutil.isRed(nChessType))
					{
						nRedValue += m_chessValue[i][j];	
					}
					else
					{
						nBlackValue += m_chessValue[i][j];	
					}
				}
			}
			if (bIsRedTurn != 0)
			{
				return nRedValue - nBlackValue;
			}
			else
			{
				return  nBlackValue-nRedValue ;
			}
	}
	
	/**
	 * ö�ٸ���λ���ϵ��������λ��
	 * @param position
	 * @param j
	 * @param i
	 * @return
	 */
	int GetRelatePiece(int position[][],int j, int i){
		nPosCount = 0;
		int nChessID;
		boolean flag;
		int x,y;
		
		nChessID = position[i][j];
		switch(nChessID)
		{
		case Chessconst.R_KING:
		case Chessconst.B_KING:
			
			for (y = 0; y < 3; y++)
				for (x = 3; x < 6; x++)
					if (CanTouch(position, j, i, x, y))
						AddPoint(x, y);
			for (y = 7; y < 10; y++)
				for (x = 3; x < 6; x++)
					if (CanTouch(position, j, i, x, y))
						AddPoint(x, y);
			break;
							
		case Chessconst.R_BISHOP:
			
			for (y = 7; y < 10; y++)
				for (x = 3; x < 6; x++)
					if (CanTouch(position, j, i, x, y))
						AddPoint(x, y);
			break;
					
		case Chessconst.B_BISHOP:
			
			for (y = 0; y < 3; y++)
				for (x = 3; x < 6; x++)
					if (CanTouch(position, j, i, x, y))
						AddPoint(x, y);
			break;
					
		case Chessconst.R_ELEPHANT:
		case Chessconst.B_ELEPHANT:
			
			x=j+2;
			y=i+2;
			if(x < 9 && y < 10  && CanTouch(position, j, i, x, y))
				AddPoint(x, y);
			
			x=j+2;
			y=i-2;
			if(x < 9 && y>=0  &&  CanTouch(position, j, i, x, y))
				AddPoint(x, y);
			
			x=j-2;
			y=i+2;
			if(x>=0 && y < 10  && CanTouch(position, j, i, x, y))
				AddPoint(x, y);
			
			x=j-2;
			y=i-2;
			if(x>=0 && y>=0  && CanTouch(position, j, i, x, y))
				AddPoint(x, y);
			break;
			
			case Chessconst.R_HORSE:		
			case Chessconst.B_HORSE:		
				x=j+2;
				y=i+1;
				if((x < 9 && y < 10) &&CanTouch(position, j, i, x, y))
					AddPoint(x, y);
						
						x=j+2;
						y=i-1;
						if((x < 9 && y >= 0) &&CanTouch(position, j, i, x, y))
							AddPoint(x, y);
						
						x=j-2;
						y=i+1;
						if((x >= 0 && y < 10) &&CanTouch(position, j, i, x, y))
							AddPoint(x, y);
						
						x=j-2;
						y=i-1;
						if((x >= 0 && y >= 0) &&CanTouch(position, j, i, x, y))
							AddPoint(x, y);
						
						x=j+1;
						y=i+2;
						if((x < 9 && y < 10) &&CanTouch(position, j, i, x, y))
							AddPoint(x, y);
						x=j-1;
						y=i+2;
						if((x >= 0 && y < 10) &&CanTouch(position, j, i, x, y))
							AddPoint(x, y);
						x=j+1;
						y=i-2;
						if((x < 9 && y >= 0) &&CanTouch(position, j, i, x, y))
							AddPoint(x, y);
						x=j-1;
						y=i-2;
						if((x >= 0 && y >= 0) &&CanTouch(position, j, i, x, y))
							AddPoint(x, y);
						break;
						
					case Chessconst.R_CAR:
					case Chessconst.B_CAR:
						x=j+1;
						y=i;
						while(x < 9)
						{
							if( Chessconst.NOCHESS == position[y][x] )
								AddPoint(x, y);
							else
							{
								AddPoint(x, y);
								break;
							}
							x++;
						}
						
						x = j-1;
						y = i;
						while(x >= 0)
						{
							if( Chessconst.NOCHESS == position[y][x] )
								AddPoint(x, y);
							else
							{
								AddPoint(x, y);
								break;
							}
							x--;
						}
						
						x=j;
						y=i+1;//
						while(y < 10)
						{
							if( Chessconst.NOCHESS == position[y][x])
								AddPoint(x, y);
							else
							{
								AddPoint(x, y);
								break;
							}
							y++;
						}
						
						x = j;
						y = i-1;
						while(y>=0)
						{
							if( Chessconst.NOCHESS == position[y][x])
								AddPoint(x, y);
							else
							{
								AddPoint(x, y);
								break;
							}
							y--;
						}
						break;
						
					case Chessconst.R_PAWN:
						y = i - 1;
						x = j;
						
						if(y >= 0)
							AddPoint(x, y);
						
						if(i < 5)
						{
							y=i;
							x=j+1;
							if(x < 9 )
								AddPoint(x, y);
							x=j-1;
							if(x >= 0 )
								AddPoint(x, y);
						}
						break;
						
					case Chessconst.B_PAWN:
						y = i + 1;
						x = j;
						
						if(y < 10 )
							AddPoint(x, y);
						
						if(i > 4)
						{
							y=i;
							x=j+1;
							if(x < 9)
								AddPoint(x, y);
							x=j-1;
							if(x >= 0)
								AddPoint(x, y);
						}
						break;
						
					case Chessconst.B_CANON:
					case Chessconst.R_CANON:
						
						x=j+1;		//�k
						y=i;
						flag=false;
						while(x < 9)		
						{
							if( Chessconst.NOCHESS == position[y][x] )
							{
								if(!flag)
									AddPoint(x, y);
							}
							else
							{
								if(!flag)
									flag=true;
								else 
								{
									AddPoint(x, y);
									break;
								}
							}
							x++;
						}
						
						x=j-1;
						flag=false;	
						while(x>=0)
						{
							if( Chessconst.NOCHESS == position[y][x] )
							{
								if(!flag)
									AddPoint(x, y);
							}
							else
							{
								if(!flag)
									flag=true;
								else 
								{
									AddPoint(x, y);
									break;
								}
							}
							x--;
						}
						x=j;	
						y=i+1;
						flag=false;
						while(y < 10)
						{
							if( Chessconst.NOCHESS == position[y][x] )
							{
								if(!flag)
									AddPoint(x, y);
							}
							else
							{
								if(!flag)
									flag=true;
								else 
								{
									AddPoint(x, y);
									break;
								}
							}
							y++;
						}
						
						y=i-1;	//�W
						flag=false;	
						while(y>=0)
						{
							if( Chessconst.NOCHESS == position[y][x] )
							{
								if(!flag)
									AddPoint(x, y);
							}
							else
							{
								if(!flag)
									flag=true;
								else 
								{
									AddPoint(x, y);
									break;
								}
							}
							y--;
						}
						break;
						
					default:
						break;
						
					}
					return nPosCount ;	
	}
	
	/**
	 * �ж�����position��λ��From�������ܷ��ߵ�λ��To
	 * @param position
	 * @param nFromX
	 * @param nFromY
	 * @param nToX
	 * @param nToY
	 * @return
	 */
	boolean CanTouch(int position[][], int nFromX, int nFromY, int nToX, int nToY){
		int i = 0, j = 0;
		int nMoveChessID, nTargetID;
		if (nFromY ==  nToY && nFromX == nToX)
			return false;//Ŀ����Դ��ͬ
		
		nMoveChessID = position[nFromY][nFromX];
		nTargetID = position[nToY][nToX];
		
		switch(nMoveChessID) 
		{
		case Chessconst.B_KING:   // ��˧  
			if (nTargetID == Chessconst.R_KING) // �Ͻ�����?
			{
				if (nFromX != nToX)// �������Ƿ����
					return false;
				for (i = nFromY + 1; i < nToY; i++)
					if (position[i][nFromX] != Chessconst.NOCHESS)
						return false;// �м�������ӣ�����false
			}
			else
			{
				if (nToY > 2 || nToX > 5 || nToX < 3)
					return false;//Ŀ����ھŹ�֮��
				if(Math.abs(nFromY - nToY) + Math.abs(nToX - nFromX) > 1) 
					return false;//��˧ֻ��һ��ֱ��:
			}
			break;
			
		case Chessconst.R_KING: // �콫    
			if (nTargetID == Chessconst.B_KING)//�Ͻ�����?
			{
				if (nFromX != nToX)
					return false;//����������ͬһ��
				for (i = nFromY - 1; i > nToY; i--)
					if (position[i][nFromX] != Chessconst.NOCHESS)
						return false;//�м��б����
			}
			else
			{
				if (nToY < 7 || nToX > 5 || nToX < 3)
					return false;//Ŀ����ھŹ�֮��
				if(Math.abs(nFromY - nToY) + Math.abs(nToX - nFromX) > 1) 
					return false;//��˧ֻ��һ��ֱ��:
			}
			break;
			
		case Chessconst.R_BISHOP:  // ��ʿ
			if (nToY < 7 || nToX > 5 || nToX < 3)
				return false;//ʿ���Ź�	
			if (Math.abs(nFromY - nToY) != 1 || Math.abs(nToX - nFromX) != 1)
				return false;	//ʿ�߱�����б��
			break;
			
		case Chessconst.B_BISHOP:   //��ʿ
			if (nToY > 2 || nToX > 5 || nToX < 3)
				return false;//ʿ���Ź�	
			if (Math.abs(nFromY - nToY) != 1 || Math.abs(nToX - nFromX) != 1)
				return false;	//ʿ��б��
			break;
			
		case Chessconst.R_ELEPHANT: //����
			if(nToY < 5)
				return false;//�಻�ܹ���
			if(Math.abs(nFromX-nToX) != 2 || Math.abs(nFromY-nToY) != 2)
				return false;//��������
			if(position[(nFromY + nToY) / 2][(nFromX + nToX) / 2] != Chessconst.NOCHESS)
				return false;//���۱���ס��
			break;
			
		case Chessconst.B_ELEPHANT://���� 
			if(nToY > 4)
				return false;//�಻�ܹ���
			if(Math.abs(nFromX-nToX) != 2 || Math.abs(nFromY-nToY) != 2)
				return false;//��������
			if(position[(nFromY + nToY) / 2][(nFromX + nToX) / 2] != Chessconst.NOCHESS)
				return false;//���۱���ס��
			break;
			
		case Chessconst.B_PAWN:     //�ڱ�
			if(nToY < nFromY)
				return false;//������ͷ
			if( nFromY < 5 && nFromY == nToY)
				return false;//������ǰֻ��ֱ��
			if(nToY - nFromY + Math.abs(nToX - nFromX) > 1)
				return false;//��ֻ��һ��ֱ��:
			break;
			
		case Chessconst.R_PAWN:    //���
			if(nToY > nFromY)
				return false;//������ͷ
			if( nFromY > 4 && nFromY == nToY)
				return false;//������ǰֻ��ֱ��
			if(nFromY - nToY + Math.abs(nToX - nFromX) > 1)
				return false;//��ֻ��һ��ֱ��:
			break;
		
			
		case Chessconst.B_CAR:  // �ڳ�    
		case Chessconst.R_CAR:  // �쳵    
			
			if(nFromY != nToY && nFromX != nToX)
				return false;	//����ֱ��:
			if(nFromY == nToY)
			{
				if(nFromX < nToX)
				{
					for(i = nFromX + 1; i < nToX; i++)
						if(position[nFromY][i] != Chessconst.NOCHESS)
							return false;// �м䲻��������
				}
				else
				{
					for(i = nToX + 1; i < nFromX; i++)
						if(position[nFromY][i] != Chessconst.NOCHESS)
							return false;
				}
			}
			else
			{
				if(nFromY < nToY)
				{
					for(j = nFromY + 1; j < nToY; j++)
						if(position[j][nFromX] != Chessconst.NOCHESS)
							return false;
				}
				else
				{
					for(j= nToY + 1; j < nFromY; j++)
						if(position[j][nFromX] != Chessconst.NOCHESS)
							return false;
				}
			}
			
			break;
			
		case Chessconst.B_HORSE:   // ���� 
		case Chessconst.R_HORSE:   // ���� 
			
			if(!((Math.abs(nToX-nFromX)==1 && Math.abs(nToY-nFromY)==2)
				||(Math.abs(nToX-nFromX)==2&& Math.abs(nToY-nFromY)==1)))
				return false;//��������
			if	(nToX-nFromX==2)
			{
				i=nFromX+1;
				j=nFromY;
			}
			else if	(nFromX-nToX==2)
			{
				i=nFromX-1;
				j=nFromY;
			}
			else if	(nToY-nFromY==2)
			{
				i=nFromX;
				j=nFromY+1;
			}
			else if	(nFromY-nToY==2)
			{
				i=nFromX;
				j=nFromY-1;
			}
			if(position[j][i] != Chessconst.NOCHESS)
				return false; //������
			break;
		
		case Chessconst.B_CANON: // ����
		case Chessconst.R_CANON: // ����   
			
			if(nFromY!=nToY && nFromX!=nToX)
				return false;	//����ֱ��:
			
			//�ڲ�����ʱ��������·���в���������
			if(position[nToY][nToX] == Chessconst.NOCHESS)
			{
				if(nFromY == nToY)
				{
					if(nFromX < nToX)
					{
						for(i = nFromX + 1; i < nToX; i++)
							if(position[nFromY][i] != Chessconst.NOCHESS)
								return false;
					}
					else
					{
						for(i = nToX + 1; i < nFromX; i++)
							if(position[nFromY][i]!= Chessconst.NOCHESS)
								return false;
					}
				}
				else
				{
					if(nFromY < nToY)
					{
						for(j = nFromY + 1; j < nToY; j++)
							if(position[j][nFromX] != Chessconst.NOCHESS)
								return false;
					}
					else
					{
						for(j = nToY + 1; j < nFromY; j++)
							if(position[j][nFromX] != Chessconst.NOCHESS)
								return false;
					}
				}
			}
			else	//�ڳ���ʱ���м����ֻ��һ������
			{
				int count=0;
				if(nFromY == nToY)
				{
					if(nFromX < nToX)
					{
						for(i=nFromX+1;i<nToX;i++)
							if(position[nFromY][i] != Chessconst.NOCHESS)
								count++;
							if(count != 1)
								return false;
					}
					else
					{
						for(i=nToX+1;i<nFromX;i++)
							if(position[nFromY][i] != Chessconst.NOCHESS)
								count++;
							if(count!=1)
								return false;
					}
				}
				else
				{
					if(nFromY<nToY)
					{
						for(j=nFromY+1;j<nToY;j++)
							if(position[j][nFromX]!= Chessconst.NOCHESS)
								count++;
							if(count!=1)
								return false;
					}
					else
					{
						for(j=nToY+1;j<nFromY;j++)
							if(position[j][nFromX] != Chessconst.NOCHESS)
								count++;
							if(count!=1)
								return false;
					}
				}
			}
			break;
		default:
			return false;
		}
		return true;
	}
	/**
	 * ��һ��λ�ü��뵽����RelatePos��
	 * @param x
	 * @param y
	 */
	void AddPoint(int x, int y){
		if(RelatePos[nPosCount] == null)
			RelatePos[nPosCount] = new Chessmanpos();
		RelatePos[nPosCount].x = x;
		RelatePos[nPosCount].y = y;
		nPosCount++;
	}
	
}
