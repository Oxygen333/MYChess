package algorithm;

// �߷�������
public class MoveGenerator{
	
	public Chessmov[][] m_MoveList = new Chessmov[8][80];//���ÿһ��������߷�
	public int m_nMoveCount;// ��¼m_MoveList���߷�������
	
	public MoveGenerator(){
		super();
	}

	public boolean IsValidMove(int position[][], int nFromX, int nFromY, int nToX, int nToY){
		if (nToX < 0 || nFromX < 0) {// ����߳���ʱ
			return false;
		}
		if (nToX > 8 || nFromX > 8) {// ���ұ߳���ʱ
			return false;
		}
		if (nToY < 0 || nFromY < 0) {// ���ϱ߳���ʱ
			return false;
		}
		if (nToY > 9 || nFromY > 9) {// ���±߳���ʱ
			return false;
		}
		int i = 0, j = 0;
		int nMoveChessID, nTargetID;
		if (nFromY ==  nToY && nFromX == nToX)
			return false;//Ŀ����Դ��ͬ
		nMoveChessID = position[nFromY][nFromX];
		nTargetID = position[nToY][nToX];
		if (Chessutil.isSameSide(nMoveChessID, nTargetID))
			return false;//���ܳ��Լ�����
		
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
	 * 
	 * @param position ��ǰ���ӵĶ�ά����
	 * @param nPly ��ǰ�����Ĳ��� �� ÿ�㽫�߷����ڲ�ͬ��λ��
	 * @param nSide ָ��������һ�����߷���1Ϊ�췽��0Ϊ�ڷ�
	 * @return
	 */
	
	public int CreatePossibleMove(int position[][], int nPly, int nSide){
		int	nChessID;
		int	i,j;
		
		m_nMoveCount = 0;
		
		for (i = 0; i < 10; i++)
			for (j = 0; j < 9; j++)
			{
				if (position[i][j] != Chessconst.NOCHESS)
				{
					nChessID = position[i][j];
					if (nSide == 0 && Chessutil.isRed(nChessID))
						continue;// ������������߷�����������
					if (nSide != 0 && Chessutil.isBlack(nChessID))
						continue;//������������߷�����������
					switch(nChessID)
					{
					case Chessconst.R_KING://�콫
					case Chessconst.B_KING://��˧
						Gen_KingMove(position, i, j, nPly);
						break;
						
					case Chessconst.R_BISHOP://��ʿ 
						Gen_RBishopMove(position, i, j, nPly);
						break;
						
					case Chessconst.B_BISHOP://��ʿ
						Gen_BBishopMove(position, i, j, nPly);
						break;
						
					case Chessconst.R_ELEPHANT://����
					case Chessconst.B_ELEPHANT://����
						Gen_ElephantMove(position, i, j, nPly);
						break;
						
					case Chessconst.R_HORSE: //����		
					case Chessconst.B_HORSE: //����		
						Gen_HorseMove(position, i, j, nPly);
						break;
						
					case Chessconst.R_CAR://�쳵
					case Chessconst.B_CAR://�ڳ�
						Gen_CarMove(position, i, j, nPly);
						break;
						
					case Chessconst.R_PAWN://����
						Gen_RPawnMove(position, i, j, nPly);
						break;
						
					case Chessconst.B_PAWN://��ʿ
						Gen_BPawnMove(position, i, j, nPly);
						break;
						
					case Chessconst.B_CANON://����
					case Chessconst.R_CANON://����
						Gen_CanonMove(position, i, j, nPly);
						break;
						
					default:
						break;
						
					}// end of switch
				}
			}
			
		return m_nMoveCount; // �����ܵ��߷���Ŀ
	}

	/**
	 * ˧/��
	 */
	public void Gen_KingMove(int[][] position, int i, int j, int nPly) {
		int x,  y;
		for (y = 0; y < 3; y++)
			for (x = 3; x < 6; x++)
				if (IsValidMove(position, j, i, x, y))
					AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		for (y = 7; y < 10; y++)
			for (x = 3; x < 6; x++)
				if (IsValidMove(position, j, i, x, y))
					AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
	}

	/**
	 * ��ʿ
	 */
	public void Gen_RBishopMove(int[][] position, int i, int j, int nPly) {
		int x,  y;
		for (y = 7; y < 10; y++)
			for (x = 3; x < 6; x++)
				if (IsValidMove(position, j, i, x, y))
					AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		
	}

	/**
	 * ��ʿ
	 */
	
	public void Gen_BBishopMove(int[][] position, int i, int j, int nPly) {
		int x,  y;
		for (y = 0; y < 3; y++)
			for (x = 3; x < 6; x++)
				if (IsValidMove(position, j, i, x, y))
					AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
	}

	/**
	 * ��/��4������
	 */
	
	public void Gen_ElephantMove(int[][] position, int i, int j, int nPly) {
		int x,  y;

		x=j+2;
		y=i+2;
		if(x < 9 && y < 10  && IsValidMove(position, j, i, x, y))
			AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		
		x=j+2;
		y=i-2;
		if(x < 9 && y>=0  &&  IsValidMove(position, j, i, x, y))
			AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		
		x=j-2;
		y=i+2;
		if(x>=0 && y < 10  && IsValidMove(position, j, i, x, y))
			AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		
		x=j-2;
		y=i-2;
		if(x>=0 && y>=0  && IsValidMove(position, j, i, x, y))
			AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
	}

	/**
	 * ��8������
	 */
	
	public void Gen_HorseMove(int[][] position, int i, int j, int nPly) {
		int x,  y;

		x=j+2;
		y=i+1;
		if((x < 9 && y < 10) &&IsValidMove(position, j, i, x, y))
			AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		
		x=j+2;
		y=i-1;
		if((x < 9 && y >= 0) &&IsValidMove(position, j, i, x, y))
			AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		
		x=j-2;
		y=i+1;
		if((x >= 0 && y < 10) &&IsValidMove(position, j, i, x, y))
			AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		
		x=j-2;
		y=i-1;
		if((x >= 0 && y >= 0) &&IsValidMove(position, j, i, x, y))
			AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		
		x=j+1;
		y=i+2;
		if((x < 9 && y < 10) &&IsValidMove(position, j, i, x, y))
			AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		x=j-1;
		y=i+2;
		if((x >= 0 && y < 10) &&IsValidMove(position, j, i, x, y))
			AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		x=j+1;
		y=i-2;
		if((x < 9 && y >= 0) &&IsValidMove(position, j, i, x, y))
			AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		x=j-1;
		y=i-2;
		if((x >= 0 && y >= 0) &&IsValidMove(position, j, i, x, y))
			AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
	}

	/**
	 * ����
	 */
	
	public void Gen_RPawnMove(int[][] position, int i, int j, int nPly) {
		int x, y;
		int nChessID;

		nChessID = position[i][j];

		y = i - 1;//��ǰ
		x = j;
		if(y > 0 && !Chessutil.isSameSide(nChessID, position[y][x]))
			AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		
		if(i < 5) //�Ƿ��Ѿ�����
		{
			y=i;
			x=j+1;
			if(x < 9 && !Chessutil.isSameSide(nChessID, position[y][x]))
				AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
			x=j-1;
			if(x >= 0 && !Chessutil.isSameSide(nChessID, position[y][x]))
				AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		}
		
	}

	/**
	 * �ڱ�
	 */
	public void Gen_BPawnMove(int[][] position, int i, int j, int nPly) {
		int x, y;
		int nChessID;

		nChessID = position[i][j];

		y = i + 1;
		x = j;
		if(y < 10 && !Chessutil.isSameSide(nChessID, position[y][x]))
			AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		
		if(i > 4)
		{
			y=i;
			x=j+1;
			if(x < 9 && !Chessutil.isSameSide(nChessID, position[y][x]))
				AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
			x=j-1;
			if(x >= 0 && !Chessutil.isSameSide(nChessID, position[y][x]))
				AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
		}
	}

	/**
	 * ����4������
	 */
	
	public void Gen_CarMove(int[][] position, int i, int j, int nPly) {
		int x,  y;
		int nChessID;

		nChessID = position[i][j];
		
		x=j+1;
		y=i;
		while(x < 9)
		{
			if( Chessconst.NOCHESS == position[y][x] )
				AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
			else
			{
				if(!Chessutil.isSameSide(nChessID, position[y][x]))
					AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
				break;
			}
			x++;
		}
		
		x = j-1;
		y = i;
		while(x >= 0)
		{
			if( Chessconst.NOCHESS == position[y][x] )
				AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
			else
			{
				if(!Chessutil.isSameSide(nChessID, position[y][x]))
					AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
				break;
			}
			x--;
		}
		
		x=j;
		y=i+1;//
		while(y < 10)
		{
			if( Chessconst.NOCHESS == position[y][x])
				AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
			else
			{
				if(!Chessutil.isSameSide(nChessID, position[y][x]))
					AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
				break;
			}
			y++;
		}
		
		x = j;
		y = i-1;//
		while(y>=0)
		{
			if( Chessconst.NOCHESS == position[y][x])
				AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
			else
			{
				if(!Chessutil.isSameSide(nChessID, position[y][x]))
					AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
				break;
			}
			y--;
		}
	}
	
	/**
	 * �ڣ�4������
	 */
	
	public void Gen_CanonMove(int[][] position, int i, int j, int nPly) {
		int x, y;
		boolean	flag;
		int nChessID;

		nChessID = position[i][j];
		
		x=j+1;		//�k
		y=i;
		flag=false;
		while(x < 9)		
		{
			if( Chessconst.NOCHESS == position[y][x] )
			{
				if(!flag)
					AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
			}
			else
			{
				if(!flag)
					flag=true;
				else 
				{
					if(!Chessutil.isSameSide(nChessID, position[y][x]))
						AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
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
					AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
			}
			else
			{
				if(!flag)
					flag=true;
				else 
				{
					if(!Chessutil.isSameSide(nChessID, position[y][x]))
						AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
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
					AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
			}
			else
			{
				if(!flag)
					flag=true;
				else 
				{
					if(!Chessutil.isSameSide(nChessID, position[y][x]))
						AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
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
					AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
			}
			else
			{
				if(!flag)
					flag=true;
				else 
				{
					if(!Chessutil.isSameSide(nChessID, position[y][x]))
						AddMove(position[i][j],position[y][x],j, i, x, y, nPly);
					break;
				}
			}
			y--;
		}
		
	}

	
	public int AddMove(int chessId,int chessId2 ,int nFromX, int nFromY,int nToX, int nToY, int nPly) {
		m_MoveList[nPly][m_nMoveCount] = new Chessmov();
		m_MoveList[nPly][m_nMoveCount].chessId = chessId;
		m_MoveList[nPly][m_nMoveCount].chessId2 = chessId2;
		m_MoveList[nPly][m_nMoveCount].From = new Chessmanpos();
		m_MoveList[nPly][m_nMoveCount].From.x = nFromX;
		m_MoveList[nPly][m_nMoveCount].From.y = nFromY;
		m_MoveList[nPly][m_nMoveCount].To = new Chessmanpos();
		m_MoveList[nPly][m_nMoveCount].To.x = nToX;
		m_MoveList[nPly][m_nMoveCount].To.y = nToY;
		m_nMoveCount++;
		return m_nMoveCount;
	}
	
}
