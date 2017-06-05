package info.anatolko.tl.domain;

public class TrafficLightState {

    private Color currentLight;
    private Color nextLight;
    private int timer;
    private boolean buttonState;
    private boolean onServiceMode;

    public TrafficLightState() {
        currentLight = Color.GREEN;
        nextLight = Color.GREEN;
    }

    public Color getCurrentLight() {
        return currentLight;
    }


    /**
     * Get current light in ColorLog format.
     *
     * @return current light in ColorLog format
     */
    public ColorLog getCurrentColorLog() {
        return new ColorLog(currentLight);
    }

    public void setCurrentLight(Color currentLight) {
        this.currentLight = currentLight;
    }

    public Color getNextLight() {
        return nextLight;
    }

    public void setNextLight(Color nextLight) {
        this.nextLight = nextLight;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    /**
     * Add 1 second to timer.
     *
     * @return current timer
     */
    public int incTimer() {
        this.timer++;
        return timer;
    }

    public boolean isButtonState() {
        return buttonState;
    }

    public void setButtonState(boolean buttonState) {
        this.buttonState = buttonState;
    }

    public boolean isOnServiceMode() {
        return onServiceMode;
    }

    public void setOnServiceMode(boolean onServiceMode) {
        this.onServiceMode = onServiceMode;
    }
}
