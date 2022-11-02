package za.co.sacpo.grant.xCubeLib.dataObj;

public class QAObj {
    public QAObj(String quizNo, String opA, String opB, String opC, String opD) {
        this.quizNo = quizNo;
        this.opA = opA;
        this.opB = opB;
        this.opC = opC;
        this.opD = opD;
    }

   public String getQuizNo() {
        return quizNo;
    }


    public void setQuizNo(String quizNo) {
        this.quizNo = quizNo;

    }

    public String getOpA() {
        return opA;
    }

    public void setOpA(String opA) {
        this.opA = opA;
    }

    public String getOpB() {
        return opB;
    }

    public void setOpB(String opB) {
        this.opB = opB;
    }

    public String getOpC() {
        return opC;
    }

    public void setOpC(String opC) {
        this.opC = opC;
    }

    public String getOpD() {
        return opD;
    }

    public void setOpD(String opD) {
        this.opD = opD;
    }

    public String getQui_titile() {
        return Qui_titile;
    }

    public void setQui_titile(String qui_titile) {
        Qui_titile = qui_titile;
    }

    private String quizNo;
    private String opA;
    private String opB;
    private String opC;
    private String opD;

    public QAObj(String quizNo, String opA, String opB, String opC, String opD, String qui_titile) {
        this.quizNo = quizNo;
        this.opA = opA;
        this.opB = opB;
        this.opC = opC;
        this.opD = opD;
        Qui_titile = qui_titile;
    }

    private String Qui_titile;

}
