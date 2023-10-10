public class LoopData {
    private Integer lineNo;
    private String variable;
    private Integer target;
    public LoopData(String variable, String target) {
        this.variable = variable;
        this.target = Integer.parseInt(target);
    }

    public void setLineNo(Integer n) {
        this.lineNo = n;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public String getVariable() {
        return variable;
    }
    public Integer getTarget() {
        return target;
    }
}