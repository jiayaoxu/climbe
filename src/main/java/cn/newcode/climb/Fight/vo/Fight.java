package cn.newcode.climb.Fight.vo;

/**
 * @Description: 对战传包
 * @author: shine
 * @CreateDate: 2017/10/29 10:29
 * @Version: 1.0
 */
public class Fight {
    /**
     * 前面七个:
     * float
     * 位置xyz
     * 角度xyz
     * forward
     * 后面四个:
     * 小球位置String
     * 只要有一个值更新,就传输给对手
     */
    private  float positionX;
    private  float positionY;
    private  float positionZ;

    private  float angleX;
    private  float angleY;
    private  float angleZ;

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public float getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(float positionZ) {
        this.positionZ = positionZ;
    }

    public float getAngleX() {
        return angleX;
    }

    public void setAngleX(float angleX) {
        this.angleX = angleX;
    }

    public float getAngleY() {
        return angleY;
    }

    public void setAngleY(float angleY) {
        this.angleY = angleY;
    }

    public float getAngleZ() {
        return angleZ;
    }

    public void setAngleZ(float angleZ) {
        this.angleZ = angleZ;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getGlobeA() {
        return globeA;
    }

    public void setGlobeA(String globeA) {
        this.globeA = globeA;
    }

    public String getGlobeB() {
        return globeB;
    }

    public void setGlobeB(String globeB) {
        this.globeB = globeB;
    }

    public String getGlobeC() {
        return globeC;
    }

    public void setGlobeC(String globeC) {
        this.globeC = globeC;
    }

    private  String forward;

    private  String globeA;
    private  String globeB;
    private  String globeC;
}
