package algorithm;

/**
 * Alpha-Beta �����㷨
 */
public class AlphaBetaEngine extends SearchEngine{
	
	public AlphaBetaEngine(){
		super();
	}
	
	public void SearchAGoodMove(int position[][])
	{
		for(int i=0;i<10;i++){  // ���浱ǰ������Ϣ
			for(int j=0;j<9;j++){
				CurPosition[i][j] = position[i][j];
			}
		}
		m_nMaxDepth = m_nSearchDepth; //��ǰ������
		
		/*
		 *  �����㷨���õ� BestMove
		 *  һ���� �ܴ� һ�� �� �ܴ�
		 */
		alphabeta(m_nMaxDepth, -20000, 20000); 
		
		Makemove(m_cmBestMove); //����һ��
		
		for(int i=0;i<10;i++){    // �����ƶ���������Ϣ����
			for(int j=0;j<9;j++){
				position[i][j] = CurPosition[i][j];
			}
		}
	}

	/**
	 * �ڷ������ �� �췽�����
	 */
	public int alphabeta(int depth, int alpha, int beta)
	{
		int i = IsGameOver(CurPosition, depth);
		if (i != 0)
			return i;

		if (depth <= 0)	//Ҷ�ӽڵ�ȡ��ֵ
			return m_pEval.Eveluate(CurPosition, (m_nMaxDepth-depth)%2);
		
		int count = m_pMG.CreatePossibleMove(CurPosition, depth, (m_nMaxDepth-depth)%2);

		for (i=0;i<count;i++) 
		{

			int type = Makemove(m_pMG.m_MoveList[depth][i]);
			int score = -alphabeta(depth - 1, -beta, -alpha); // �ݹ�
			UnMakemove(m_pMG.m_MoveList[depth][i],type); 

			if (score > alpha)
			{
				alpha = score;
				if(depth == m_nMaxDepth)
					m_cmBestMove = m_pMG.m_MoveList[depth][i];
			}
	        if (alpha >= beta)  
	              break;
		}
		return alpha;
	}
}
