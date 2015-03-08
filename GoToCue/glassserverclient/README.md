### Notes on third party dependencies

The glass server client module utilizes the Unirest library by
[Mashape](http://unirest.io). The Unirest library depends on
Apache's HTTP components library. A version of HTTP components is already 
packaged within Android's SDK. To prevent possible issues with different versions
of the same jar on the classpath, the Unirest library and its dependencies
is packaged into its own fat jar which is checked into the './lib' directory.

Directions on how to create the fat jar can be found on the Mashape 
[blog](http://blog.mashape.com/using-unirest-java-for-your-android-projects/).