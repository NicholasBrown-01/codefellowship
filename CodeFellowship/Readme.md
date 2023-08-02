# README

## Lab 16: CodeFellowship Login

### Feature Tasks:

- Build an app that allows users to log into CodeFellowship.
- The site should have a login page.
- The login page should have a link to a signup page.
- An ApplicationUser should have a username, password (will be hashed using BCrypt), firstName, lastName, dateOfBirth, bio, and any other fields you think are useful.
- All of these fields must be set at signup. They will not be editable at any other time.
- The site should allow users to create an ApplicationUser on the “sign up” page.
- Your Controller should have an @Autowired private PasswordEncoder passwordEncoder; and use that to run passwordEncoder.encode(password) before saving the password into the new user.
- Using the cheat sheet above in “Resources”, add the ability for users to log in to your app.
- The site should have a homepage at the root route (/) that contains basic information about the site.
- This page should have links to login and signup if the user is NOT logged in.
- This page should have a link to logout if the user IS logged in.
- Ensure that users can log out and are redirected to the home page or login page.
- When a user is logged in, the app should display the user’s username on every page (probably in the heading).
- Ensure that your homepage, login, and registration routes are accessible to non-logged in users.
- The site should be well-styled and attractive.
- The site should use templates to display its information.
- Ensure that user registration also logs users into your app automatically.


## Lab 17: CodeFellowship Posts with Login

### Feature Tasks:

- Added myprofile page
- Added ability for user to see profile information
- Added ability for user to make posts

- Not Complete:User to view other User Profiles