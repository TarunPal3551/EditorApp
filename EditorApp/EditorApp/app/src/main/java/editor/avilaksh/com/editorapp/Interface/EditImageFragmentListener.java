package editor.avilaksh.com.editorapp.Interface;

/**
 * Created by mahmoud on 21/07/18.
 */

public interface EditImageFragmentListener {

    void onBrightnessChanged(int brightness);
    void onSaturationChanged(float saturation);
    void onConstrantChanged(float constrant);
    void onEditStarted();
    void onEditCompleted();


}
