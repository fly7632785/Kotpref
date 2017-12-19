# Kotpref

Android SharedPreference delegation for Kotlin.

[![wercker status](https://app.wercker.com/status/dd188c571c2416d90eb24133d9bcfa83/s/master "wercker status")](https://app.wercker.com/project/byKey/dd188c571c2416d90eb24133d9bcfa83) [![kotlin](https://img.shields.io/badge/kotlin-1.1.51-blue.svg)]() [![codecov](https://codecov.io/gh/chibatching/Kotpref/branch/master/graph/badge.svg)](https://codecov.io/gh/chibatching/Kotpref) [![license](https://img.shields.io/github/license/chibatching/Kotpref.svg?maxAge=2592000)]()

## Install

**Kotpref version 2 has destructive changes from version 1**

```groovy
dependencies {
    compile "com.chibatching.kotpref:kotpref:2.2.0"
    compile "com.chibatching.kotpref:initializer:2.2.0" // optional
    compile "com.chibatching.kotpref:enum-support:2.2.0" // optional
    compile "com.chibatching.kotpref:gson-support:2.2.0" // optional
    compile 'com.github.fly7632785:KotprefEncryptSupport:1.0.0' //optional
}
```

## How to use

### Declare preference model

```kotlin
object UserInfo : KotprefModel() {
    var gameLevel by enumValuePref(GameLevel.NORMAL)
    var name by stringPref()
    var code by nullableStringPref()
    var age by intPref(default = 14)
    var highScore by longPref()
    var rate by floatPref()
    val prizes by stringSetPref {
        val set = TreeSet<String>()
        set.add("Beginner")
        return@stringSetPref set
    }
}

enum class GameLevel {
    EASY,
    NORMAL,
    HARD
}
```

### Set up

Pass the application context to Kotpref

```kotlin
Kotpref.init(context)
```

or use auto initializer module.

### Read and Write

```kotlin
UserInfo.gameLevel = GameLevel.HARD
UserInfo.name = "chibatching"
UserInfo.code = "DAEF2599-7FC9-49C5-9A11-3C12B14A6898"
UserInfo.age = 30
UserInfo.highScore = 49219902
UserInfo.rate = 49.21F
UserInfo.prizes.add("Bronze")
UserInfo.prizes.add("Silver")
UserInfo.prizes.add("Gold")

Log.d(TAG, "Game level: ${UserInfo.gameLevel}")
Log.d(TAG, "User name: ${UserInfo.name}")
Log.d(TAG, "User code: ${UserInfo.code}")
Log.d(TAG, "User age: ${UserInfo.age}")
Log.d(TAG, "User high score: ${UserInfo.highScore}")
Log.d(TAG, "User rate: ${UserInfo.rate}")
UserInfo.prizes.forEachIndexed { i, s -> Log.d(TAG, "prize[$i]: ${s}") }
```

### Bulk edit

```kotlin
UserInfo.bulk {
    gameLevel = GameLevel.EASY
    name = "chibatching Jr"
    code = "451B65F6-EF95-4C2C-AE76-D34535F51B3B"
    age = 2
    highScore = 3901
    rate = 0.4F
    prizes.clear()
    prizes.add("New Born")
}

// Bulk edit uses Editor#apply() method internally.
// If you want to apply immediately, you can use blockingBulk instead.
UserInfo.blockingBulk {
    gameLevel = GameLevel.EASY
}
```
### Add Encryption
```groovy
allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```
```
dependencies {
   compile 'com.github.fly7632785:KotprefEncryptSupport:1.0.0'
}
```
##### Init
Init in Application 
```
class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.ecGson = Gson()
        Kotpref.cipherAdapter = SharedPrefCipherAdapter(applicationContext)
    }
}
```
Then just use
```
    var password by ecStringPref("jafirPass")
    var code1 by ecNullableStringPref()
    var isMan by ecBooleanPref(true)
    var age1 by ecIntPref(23)
    var highScore1 by ecLongPref(1111111111L)
    var rate1 by ecFloatPref(0.5555f)
    var person1 by ecGsonPref(Person("g jafir", 21))
    var avatar21 by ecGsonPref(Avatar())
    var avatar22 by ecGsonNullablePref(Avatar())
```
#####  Advanced

If you want to custom your Cipher rules

Just implement CipherAdapter's encrypt and decrypt funs

```
class SharedPrefCipherAdapter @Throws(Exception::class)
constructor(context: Context) : CipherAdapter {
    private val secretKey: SecretKey

    init {
        this.secretKey = AESUtil.generateKey(context)
    }

    override fun encrypt(raw: String): String {
        return AESUtil.execEncrypted(secretKey, raw)
    }

    override fun decrypt(encode: String): String {
        return AESUtil.execDecrypted(secretKey, encode)
    }
}
```
And more detail in the source code, you can see it 

### Result shared preference xml

XML file name equals model class name. If model class named `UserInfo`, XML file name is `UserInfo.xml`.

```xml
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
    <long name="highScore" value="49219902" />
    <set name="prizes">
        <string>Beginner</string>
        <string>Bronze</string>
        <string>Gold</string>
        <string>Silver</string>
    </set>
    <string name="name">chibatching</string>
    <string name="code">DAEF2599-7FC9-49C5-9A11-3C12B14A6898</string>
    <int name="age" value="30" />
    <float name="rate" value="49.21" />
</map>
```

### Options

#### Change default value

```kotlin
var age: Int by intPref(18)
```

or

```kotlin
var age: Int by intPref(default = 18)
```

#### Change preference key

You can custom preference key or use from string resources.

```kotlin
var useFunc1: Boolean by booleanPref(key = "use_func1")
var mode: Int by intPref(default = 1, key = R.string.pref_mode)
```

#### Change XML file name

Override `kotprefName` property.

```kotlin
object UserInfo : KotprefModel() {
    override val kotprefName: String = "user_info"
```

#### Change SharedPreference mode

Override `kotprefMode` property. Default is `Context.MODE_PRIVATE`.

```kotlin
object UserInfo : KotprefModel() {
    override val kotprefMode: Int = Context.MODE_MULTI_PROCESS
```

### Migration from v1

#### Change function names providing delegated properties

Kotpref v2 changes function names providing delegated properties.

Example, old `stringPrefVar` is changed to `stringPref`.


### Separate auto initialization module

If you wish to use auto initialization, you should import initializer module.

## License

```
Copyright 2015-2017 Takao Chiba

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
