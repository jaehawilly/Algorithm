//이름 : 이재하
//학번 : 2017203033
//Environment : java version "16.0.1"
//              IntelliJ IDEA Community Edition 2021.1.1
import java.util.Scanner;
public class HW3_2017203033 {
    public static boolean[][] finalState;
    public static boolean[][] switchToPush;
    public static boolean[][] output;
    public static int n;
    public static int min=99999;
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        n=sc.nextInt();
        char[][] input = new char[n][n];//input char로 받음
        finalState = new boolean[n][n];//input boolean으로 저장
        switchToPush = new boolean[n][n];//Output
        output=new boolean[n][n];//save output
        for(int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                input[i][j] = sc.next().charAt(0);
                finalState[i][j]= (input[i][j]=='O');
                switchToPush[i][j]=false; //initialize to false(#)
                output[i][j]=false; //initialize to false(#)
            }
        }
        result(0,-1);
        if(min==99999){
            System.out.println("no solution.");
        }
        else {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (output[i][j]) {
                        System.out.print("O ");
                    } else System.out.print("# ");
                }
                System.out.println();
            }
        }
    }
    public static void result(int i, int j){
        if(i==n-1 && j==n-1){
            isCorrect();
            return;
        }
        if(promising(i,j)){
            if(i>n-1 || j>n-1) return; //recursion 종료조건
            if(j==n-1) { //다섯번째 열이라면
                push(finalState, i+1, 0);
                switchToPush[i+1][0] = true;
                result(i+1, 0);
                push(finalState, i+1, 0);
                switchToPush[i+1][0] = false;
                result(i+1,0);
            }
            else { //1~4번째 열이라면
                push(finalState, i, j+1);
                switchToPush[i][j+1] = true;
                result(i, j+1);
                push(finalState, i, j+1);
                switchToPush[i][j+1] = false;
                result(i, j+1);
            }
        }
    }
    public static boolean promising(int i, int j){ //promising 검사
        if(i==0){ //첫번째 줄이라면 무조건 promising(return true)
            return true;
        }
        return !finalState[i - 1][j]; //바로 위가 O라면 non-promising(return false)
    }
    public static void push(boolean[][] arr, int i, int j){ //버튼 누르고 자기 자신, 주변 상태 변화
        // 눌렀을 때 주변값 변화
        arr[i][j] = !arr[i][j];
        if(i-1>=0)
            arr[i - 1][j] = !arr[i - 1][j];
        if(i+1<n)
            arr[i + 1][j] = !arr[i + 1][j];
        if(j-1>=0)
            arr[i][j - 1] = !arr[i][j - 1];
        if(j+1<n)
            arr[i][j + 1] = !arr[i][j + 1];
    }
    public static void isCorrect(){ //최종결과(모두 다 #인 상태)가 맞는지 확인
        int cnt=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(finalState[i][j]) return; //하나라도 O가 있는 경우 return
                if(switchToPush[i][j]) cnt++; //켜져있는 개수 세준다
            }
        }
        //모두 #이 된 경우 최솟값인지 검사
        if(cnt<min){
            min=cnt;
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    output[i][j]=switchToPush[i][j];
                }
            }
        }
    }
}
