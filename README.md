# Programming Project 2 2021 - dagtraject - groep 7 - Eatee
## Mobile Application
### About
This folder contains the mobile app part of the project. 

### Technology
We used Android Studio to create the android app.

When you make an android app in Android Studio, you get the choice of which API level you want to use. The higher the level, the newer the API is and the more features it has. However, less android phones will be able to run the app. For example: if the app uses API level 16, the app can be used by 99.8% of the android phones, while API level 30 (the newest one) can only be run on less than 1% of android phones.

We chose for API level 30 so that we can use the most recent features. Even if the app would actually be rolled out to the public, the API level can easily be changed to API level 16 without a lot of problems.

### Features
- See the menu of the day
- Go to the calendar to change the date so you can see the menu on your chosen date
- Click on a product to see its allergies, price, availability and you can add it to your cart
- Create a sandwich and add it to your cart
- Change your password
- Change your 2-Factor Authentication
- View your orders
- Click an order to see more information about that order
- Able to order and pay with visa


## Website
### About
We used the Laravel framework to create our website.

### Access
To view the website you will need to be connected to the VPN of our school (EHB), then establish a connection to [eatee.be](eatee.be).

How to run the code (first time)
- go to the correct folder
- open the terminal
- enter: composter install
- enter: copy .env.example .env
- enter: php artisan key:generate
- enter: php artisan serve

#### Accounts
There are some basic accounts created, so you are able to test/view the website.
- Admin = Admin@email.com -- Administrator
- Chef = Chef@email.com -- ChefGusteau
- Other = OtherAccount@email.com -- OtherAccount

### Inspiration
For the website we were inspired by the following sites:

- [Charts](https://www.chartjs.org/docs/latest/)
- [Menu](https://tailwindcomponents.com/component/list)
- [Error/Succes Messages](https://tailwindcomponents.com/component/alert-messages)
- [Used Icons](https://heroicons.com/)
- [Modal example](https://www.tailwindtoolbox.com/components/modal)
- [Modal usage](https://www.w3schools.com/howto/howto_css_delete_modal.asp)
- [Base](https://dashboard-tailwindcomponents.netlify.app/forms/?)
- [Base](https://tailwindcomponents.com/component/dashboard-template)
- [Form](https://tailwindcomponents.com/component/form-grid)
- [Login](https://tailwindcomponents.com/component/sign-in-form-1)

### Features
#### Extra admin features
- Create new employees
- Activate/deactivate employees
- Change password of employees
- Change information of employees

#### All features available for admins, chefs, others
- See all orders that need to be prepared
- See a specific order
- Hand out/checkout an order to a customer
- See all the created menus
- Create a new menu
- Edit a menu
- Delete a menu
- See all products
- Add products to menus
- Delete products from menus
- See the stock of ingredients/products
- Create a new product
- Edit a product
- Create a new ingredient
- Create a new allergy
- Edit an allergy
- Edit an ingredient
- Propose a product to the customers
- Delete a proposed product
- Get statistics
- Edit your personal account
- Activate/deactivate two factor key
- Add a promotion to a product
- Delete a promotion
- Edit a promotion


## API
### About
Both the website and the mobile application use our API to retrieve/update something in the database.

### Technology
To create the API we used Spring Boot in Java. 
To implement two factor authentication, we used TOTP, a library that allows us to generate a key that can be entered in Google Authenticator for example.

### Features
For most of our entities in our database a route exists to create, retrieve, update or delete an entity. For more information, view the API endpoints document.
- Create new entities 
  - E.g. employees, customers, allergies, ...
- Retrieve any existing data
  - E.g. a customer with all of the orders they have placed
- Update any existing entities
- Delete data

