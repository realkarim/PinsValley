package dagger;

/**
 * Created by Karim Mostafa on 1/26/17.
 */
public class DaggerBuilder {

    private DaggerBuilder() {
    }

    private static BaseComponent baseComponent = null;

    public static BaseComponent buildDagger() {
        if(baseComponent == null)
            return DaggerBaseComponent.builder().build();

        return baseComponent;
    }
}
