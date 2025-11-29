
[![](https://jitpack.io/v/ionson100/broadcast-manager-aar.svg)](https://jitpack.io/#ionson100/broadcast-manager-aar)
##### A library for creating an array of listeners (BroadcastReceiver) based on a filter.
compileSdk = 36 minSdk = 24.
It has only five static methods.
##### Register a listener that will receive a string.

```java
// filter B1
BroadcastManager.add(this,"B1",str -> {
Toast.makeText(this,str,LENGTH_SHORT).show();
});
```
##### Register a listener that will receive an object whose class implements the Parcelable interface.

```java
import com.bitnic.broadcast_manager.*;

// B2 filter
BroadcastManager.addParcelable(this,"B2",parcelable -> {
if (parcelable instanceof TestParcelable){
TestParcelable tp= (TestParcelable) parcelable;
Toast.makeText(this,tp.toString(),LENGTH_SHORT).show();
}
});
```
##### Send a string message.
```java
BroadcastManager.sendMessage(this,"B1","test22");
```
##### Send a message to an object whose class implements the Parcelable interface.
```java
TestParcelable parcelable=new TestParcelable();
parcelable.name="name";
parcelable.age=33;
BroadcastManager.sendMessage(this,"B2",parcelable);
```
To limit the scope of message sending, you can specify the scope in a static field.
```java
BroadcastManager.broadcast_scope="com.bitnic.test";
```
##### Destroy the manager when the application closes.
```java
@Override
protected void onDestroy() {
super.onDestroy();
BroadcastManager.dispose(this);
}
```
If you specify a previously registered filter during registration, registration will fail.

##### install
```markdown
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url = uri("https://jitpack.io") }
		}
	}
```
```markdown
dependencies {
       implementation("com.github.ionson100:broadcast-manager-aar:v1.0.3")
}
```

       