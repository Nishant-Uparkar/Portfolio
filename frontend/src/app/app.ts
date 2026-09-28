import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from './navbar/navbar';
import { Home } from './home/home';
import { AboutMe } from './about-me/about-me';
import { ContactMe } from './contact-me/contact-me';
@Component({
  imports: [RouterOutlet, Navbar, Home, AboutMe, ContactMe],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('frontend');
}
