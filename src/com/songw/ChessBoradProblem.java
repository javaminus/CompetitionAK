package com.songw;

public class ChessBoradProblem {
    private int[][] board;//棋盘
    private int specialRow;//特殊的行
    private int specialCol;//特殊的列、
    private int size;
    private int type=0;
    public ChessBoradProblem(int specialRow,int specialCol,int size){
        this.specialRow=specialRow;
        this.specialCol=specialCol;
        this.size=size;
        this.board=new int[size][size];
    }
    /*
        board棋盘
        specialRow特殊行
        specialCol特殊的列
        leftRow矩阵最左边的行
        leftCol矩阵最左边的列
        size 棋盘的大小
     */
    private void ChessBoard(int specialRow,int specialCol,int leftRow,int leftCol,int size){

        if (size==1){
            return;
        }
        int subsize=size/2;
        type=type%4+1;
        int n=type;
        //特殊点在左上角
        if (specialRow<leftRow+subsize&&specialCol<leftCol+subsize){
            ChessBoard(specialRow,specialCol,leftRow,leftCol,subsize);
        }else {
            board[leftRow+subsize-1][leftCol+subsize-1]=n;
            ChessBoard(leftRow+subsize-1,leftCol+subsize-1,leftRow,leftCol,subsize);
        }
        //特殊点在右上角
        if (specialRow<leftRow+subsize&&specialCol>=leftCol+subsize){
            ChessBoard(specialRow,specialCol,leftRow,leftCol+subsize,subsize);
        }else {
            board[leftRow+subsize-1][leftCol+subsize]=n;
            ChessBoard( leftRow+subsize-1, leftCol+subsize, leftRow, leftCol+subsize, subsize);
        }
        //特殊点在左下角
        if (specialRow>=leftRow+subsize&&specialCol<leftCol+subsize){
            ChessBoard( specialRow, specialCol, leftRow+subsize, leftCol, subsize);
        }else {
            board[leftRow+subsize][leftCol+subsize-1]=n;
            ChessBoard( leftRow+subsize, leftCol+subsize-1, leftRow+subsize, leftCol, subsize);
        }
        //特殊点在右下角
        if (specialRow>=leftRow+subsize&&specialCol>=leftCol+subsize){
            ChessBoard( specialRow, specialCol, leftRow+subsize, leftCol+subsize, subsize);
        }else {
            board[leftRow+subsize][leftCol+subsize]=n;
            ChessBoard( leftRow+subsize, leftCol+subsize, leftRow+subsize, leftCol+subsize, subsize);
        }
    }
    public void prentfBoard(){
        ChessBoard(specialRow,specialCol,0,0,size);
        for (int i = 0; i <board.length ; i++) {
            for (int j = 0; j <board.length ; j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int specialRow=0;
        int specialCol=1;
        int n=4;
        ChessBoradProblem a=new ChessBoradProblem(specialRow,specialCol,n);
        a.prentfBoard();
    }

}
