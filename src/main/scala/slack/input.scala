package slack 

sealed abstract class Input

/**
 * Event generated when a key is pressed.
 *
 * @param key The key code that was pressed 
 * @param c   The character of the key that was pressed
 */
case class KeyPressed(key:Int, c: Char) extends Input


/**
 *
 * Event generated when a key is released.
 *
 * @param key The key code that was released
 * @param c   The character of the key that was released
 */
case class KeyReleased(key:Int, c: Char) extends Input
