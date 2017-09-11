# lejOS plugin for IntelliJ IDEA

**Author:** Miguel Cordova, miguelcordovadev@gmail.com

**Original repo:** https://github.com/miguelcordovam/leJOS\_plugin

**License:** Refer the the original repo

This is a version with added support for multiple OSes (*nix, Windows)

## Build and setup instructions

### Build

1. Clone the repo into your prefered directory.
2. In IntelliJ, create a new project, selecting IntelliJ Platform Plugin as the project type.
3. In the Build tab, build the project (just in case).
4. In the Build tab again, click "Prepare Plugin Module '...' For Deployment".

This will output a .jar file in your project folder, that's the plugin

### Setup (Linux)

1. Hit ctrl+alt+s, go into plugins and click "install plugin from disk..."
2. Navigate to the .jar you just created and install it.
3. Restart IntelliJ
4. Hit ctrl+alt+s again, go into tools>lejOS Plugin and set the path to the lejOS directory (downloaded separately)

At this point you should be good to go!
