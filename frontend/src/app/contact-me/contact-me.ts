import { Component, inject, signal } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'contact-me',
  imports: [FormsModule],
  templateUrl: './contact-me.html',
  styleUrl: './contact-me.css'
})
export class ContactMe {

  private http = inject(HttpClient);

  contactMsg = {
    name: '',
    email: '',
    message: ''
  };

  // Use signals for UI state
  successMessage = signal('');
  errorMessage = signal('');
  isSubmitting = signal(false);


  submitMessage(form: NgForm): void {

  // Clear previous messages
  this.successMessage.set('');
  this.errorMessage.set('');


  // =========================
  // VALIDATION
  // =========================

  if (form.invalid) {

    form.control.markAllAsTouched();


    // Name validation
    if (!this.contactMsg.name.trim()) {

      this.errorMessage.set(
        'Name is required.'
      );

    }

    else if (form.controls['name']?.errors?.['minlength']) {

      this.errorMessage.set(
        'Name must contain at least 3 characters.'
      );

    }


    // Email validation
    else if (!this.contactMsg.email.trim()) {

      this.errorMessage.set(
        'Email is required.'
      );

    }

    else if (form.controls['email']?.errors?.['email']) {

      this.errorMessage.set(
        'Please enter a valid email address.'
      );

    }


    // Message validation
    else if (!this.contactMsg.message.trim()) {

      this.errorMessage.set(
        'Message is required.'
      );

    }

    else if (form.controls['message']?.errors?.['minlength']) {

      this.errorMessage.set(
        'Message must contain at least 10 characters.'
      );

    }


    // Hide validation popup after 4 seconds
    setTimeout(() => {
      this.errorMessage.set('');
    }, 4000);


    return;
  }


  // =========================
  // SUBMITTING
  // =========================

  this.isSubmitting.set(true);


  this.http.post(
    'http://localhost:8080/contact/submit',
    this.contactMsg,
    {
      responseType: 'text'
    }
  ).subscribe({

    // =========================
    // SUCCESS
    // =========================

    next: (response) => {

      console.log('SUCCESS:', response);

      this.isSubmitting.set(false);

      this.successMessage.set(
        'Your message has been sent successfully!'
      );

      this.errorMessage.set('');


      // Clear form data
      this.contactMsg = {
        name: '',
        email: '',
        message: ''
      };

      form.resetForm();


      // Hide success popup after 4 seconds
      setTimeout(() => {
        this.successMessage.set('');
      }, 4000);

    },


    // =========================
    // ERROR
    // =========================

    error: (error) => {

      console.log('ERROR:', error);
      console.log('STATUS:', error.status);
      console.log('ERROR BODY:', error.error);


      this.isSubmitting.set(false);

      this.successMessage.set('');


      if (error.status === 409) {

        const message =
          typeof error.error === 'string'
            ? error.error
            : error.error?.message ??
              'You have already submitted a message recently.';

        this.errorMessage.set(message);

      }

      else if (error.status === 400) {

        this.errorMessage.set(
          'Please enter valid information.'
        );

      }

      else {

        this.errorMessage.set(
          'Something went wrong. Please try again later.'
        );

      }


      // Hide backend error popup after 4 seconds
      setTimeout(() => {
        this.errorMessage.set('');
      }, 4000);

    }

  });
}
}