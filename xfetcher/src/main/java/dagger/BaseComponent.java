package dagger;

import javax.inject.Singleton;

/**
 * Created by Karim Mostafa on 1/26/17.
 */
@Singleton
@Component(modules={LibModule.class})
public interface BaseComponent {
    void inject(DependenciesProvider dependenciesProvider);
}
