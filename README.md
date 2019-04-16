# What's Blitz?
![alt tag](https://png2.kisspng.com/sh/9b8e8910d1e2585894f94950ce929580/L0KzQYm3U8MyN6Z1fZH0aYP2gLBuTfNwdaF6jNd7LXnmf7B6TfFtaaNyRdZudnnmdX7sjfVzb5ZzeAs2c4nwcrFzTgNqepZzRdd2ZYLqdbBqmb02aZNrftVsMUToQLPrVb4yPGQ7SKYCNUG4QoO8U8E2OmI4SasCLoDxd1==/kisspng-computer-icons-alarm-device-emergency-symbol-siren-emergency-5abffcc14e0bd5.1436047515225315213197.png)
<br>Blitz is an Android real time form validator using a nice Kotlin DSL

# Usage
With Blitz you can validate in real time a entire form, let's create an example

For this example let's create a sign up form, in this form we just have an email and a document fields and a accept terms check box. First let's just consider that we want to enable the sign up button only when user writes a correct email in email field and fill with some value the document field, for this we can use the blitz core default validations: 

```kotlin
signup.enableWhen {
    email_field.isEmail()
    document_field.isFilled()
}
```

![alt_tag](https://media.giphy.com/media/cNI8mNhLuFyZ5mSVPU/giphy.gif)

Really simple, isn't? But now we want to improve some validations, we have a terms check box that is important in our form validation then we want to add in our validations a validation that user has selected the terms check box, for this let's use custom validations:

First, we need to create our validations class, let's extend it from Blitz core defaut validations to get all the default validations:

```kotlin
class MyCustomValidations : DefaultBlitzValidations() {
    
    fun CheckBox.isAccepted() : View = bindViewValidation(this) {
        this.isChecked
    }
}
```

then we just need to tell Blitz what validations it must use:

```kotlin
signup.enableWhenUsing(MyCustomValidations()) {
    email_field.isEmail()
    document_field.isFilled()
    terms.isAccepted()
}
```

![alt_tag](https://media.giphy.com/media/ZczdooS1nH5VtYjyWP/giphy.gif)

# Masks

In a form creation some times masks are important, thinking on that Blitz comes with an API for numeric masks, let's use our sign up example again:

Now we want to apply a mask in document field, let's consider this as a valid document `123.456.789-0`, Blitz mask api works basically considering the character `#` as a number and the rest of it part of the mask design then the correct design for our mask should be `###.###.###-#`:

```kotlin
signup.enableWhenUsing(MyCustomValidations()) {
    email_field.isEmail()
    document_field.isFilled() withMask "###.###.###-#"
    terms.isAccepted()
}
```

Just that! 

![alt_tag](https://media.giphy.com/media/JmyJW5T4EnBXd7kYRq/giphy.gif)

# Success and error cases

The best forms are those that can handle every error case from each field and Blitz provides an API for that too. Let's create a simple error and success handling for our sign up form. Now when user writes a valid email I want to show a check icon and when user writes a not valid email I want to show an alert icon:

```kotlin
private fun showSuccessCaseFor(successView: View, errorView: View) {
    successView.visibility = View.VISIBLE
    errorView.visibility = View.GONE
}

private fun showErrorCaseFor(successView: View, errorView: View) {
    successView.visibility = View.GONE
    errorView.visibility = View.VISIBLE
}

fun main() = signup.enableWhenUsing(CustomValidationExample()) {
    email_field.isEmail() onValidationSuccess {
        showSuccessCaseFor(email_success_icon, email_error_icon)
    } onValidationError {
        showErrorCaseFor(email_success_icon, email_error_icon)
    }
    document_field.isFilled() withMask "###.###.###-#"
    terms.isAccepted()
}
```

`onValidationSuccess` and `onValidationError` are functions that provides the validation state of each field.

![alt_tag](https://media.giphy.com/media/fVcGJ5a1TPc1a8ns8L/giphy.gif)

# Import

##### Gradle
```groovy
implementation 'bloder.com:blitz:0.0.1'
```

##### Maven
```xml
<dependency>
	<groupId>bloder.com</groupId>
	<artifactId>blitz</artifactId>
	<version>0.0.1</version>
	<type>pom</type>
</dependency>
```

# License

```
MIT License

Copyright (c) 2017 Bloder

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
