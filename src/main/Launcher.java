package main;

import guiClasses.StartScreenGUI;

/**
 * Group Members:
 * (35%) Eric Lin | GUI & Debugging
 * (35%) Michael Li | Logic & Git
 * (30%) Zain Muhammad | Objects & Visuals
 *
 * Date: 11/29/20
 *
 * Course Code: ICS4U
 *
 * Instructor Name: Mr. Fernandes
 *
 * Project Title: Amazing Labyrinth
 * Product Name: The Amazing Labyrinth
 *
 * Description: Recreating the board game Amazing Labyrinth
 * with some personalized touches and changes
 *
 * Features:
 *   -Traversable pathways are highlighted for the current player
 *   -Game is automatically saved and can be reloaded anytime
 *   -Player movements are animated
 *   -Very light in terms of file size (~1MB) excluding git history
 *
 * Major Skills:
 *   -Graphical user interfaces
 *   -Graph traversal algorithms
 *   -OOP practices (i.e. polymorphism and inheritance)
 *   -File writing and accessing
 *   -The use of Git as a version control system
 *
 * Areas of Concern:
 *   -Some tile images are not aligned with others, making the board seem off
 *   -Players cannot be fully customizable (i.e. player name, icon etc.)
 */
public class Launcher {
    public static void main(String[] args) {
        new StartScreenGUI();
    }
}
