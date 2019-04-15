# What's Blitz?
![alt tag](https://png2.kisspng.com/sh/9b8e8910d1e2585894f94950ce929580/L0KzQYm3U8MyN6Z1fZH0aYP2gLBuTfNwdaF6jNd7LXnmf7B6TfFtaaNyRdZudnnmdX7sjfVzb5ZzeAs2c4nwcrFzTgNqepZzRdd2ZYLqdbBqmb02aZNrftVsMUToQLPrVb4yPGQ7SKYCNUG4QoO8U8E2OmI4SasCLoDxd1==/kisspng-computer-icons-alarm-device-emergency-symbol-siren-emergency-5abffcc14e0bd5.1436047515225315213197.png)
<br>Blitz is an Android real time form validator using a nice Kotlin DSL

# Usage
With Blitz you can validate in real time a entire form, let's create an example

For this example let's create a sign up form, in this form we just have an email and a document fields and a accept terms check box. First let's just consider that we want to enable the sign up button only when user writes a correct email in email field and fill with some value the document field, for this we can use the blitz core default validations: 

```kotlin
signup_button.enableWhen {
    email_field.isEmail()
    document_field.isFilled()
}
```

Really simple, isn't? But now we want to improve some validations, we have an accept terms check box that is important in our form validation then we want to add in our validations a validation that user has selected the accept terms check box, for this let's use custom validations:

First, we need to create our validations class, let's extend it from Blitz core defaut validations to get all the default validations:

```kotlin
class MyCustomValidations : DefaultBlitzValidations() {
    
    fun CheckBox.wasSelected() : View = bindViewValidation(this) {
        this.isChecked
    }
}
```

then we just need to tell Blitz what validations it must use:

```kotlin
signup_button.enableWhenUsing(MyCustomValidations()) {
    email_field.isEmail()
    document_field.isFilled()
    checkbox.wasSelected()
}
```
