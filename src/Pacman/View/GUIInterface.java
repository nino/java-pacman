package Pacman.View;

import Pacman.lib.DialogType;

/**
 * The interface that the main view class has to implement
 */
public interface GUIInterface {
	/**
	 * To display information to the user
	 */
	public void alert(String message, String title);

	/**
	 * To get a filename from the user
	 */
	public String getFilename(DialogType dialogType);
}
