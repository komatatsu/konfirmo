# konfirmo

[![](https://jitpack.io/v/komatatsu/konfirmo.svg)](https://jitpack.io/#komatatsu/konfirmo)

konfirmo is a validation tool for Android created by kotlin.

Perform validation and button activation.  
It is necessary to set konfirmo from the code for every activity to use.

You can set multiple validation rules in one EditText.  
Messages are concatenated and output.

## Installation

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

```
dependencies {
    implementation 'com.github.komatatsu:konfirmo:v0.1'
}
```

## Usage

```kotlin
Konfirmo(context).addRuleSet(RuleSet(editText).addRule(Rule.REQUIRED)).initialize()
```

Create one RuleSet per EditText.  
Multiple rules can be set in RuleSet.

```kotlin
Konfirmo(context).addRuleSet(
    RuleSet(editText)
        .addRule(Rule.REQUIRED)
        .addRule(Rule(ValidationType.MAX_LENGTH, 20))
).initialize()
```

If you set save button, control is performed so that the button can be pressed only when all the validations are passed.

```kotlin
Konfirmo(context)
    .addSaveButton(submit)
    .addRuleSet(RuleSet(editText).addRule(Rule.REQUIRED))
).initialize()
```

## rules

- required
- max length
- min length
- max lines
- number
- date (only yyyy-MM-dd or yyyy/MM/dd)
- zip (only japanese zip code)
