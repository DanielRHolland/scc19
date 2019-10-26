package src;

import java.util.function.Consumer;

public class MenuItem {
    private String label;
    private Consumer<String> action;
    private String param;


    public MenuItem(String label, Consumer<String> action, String param) {
        this.label = label;
        this.action = action;
        this.param = param;
    }

    public String getLabel() {
        return label;
    }

    public void act(final String parameter) {
        action.accept(parameter);
    }

    public String getParam() {
        return param;
    }
}
