import { Component, signal } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
  imports: [RouterLink, RouterOutlet],
  standalone: true,
})
export class App {
  email: string = 'sohail@gmail.com';
}
