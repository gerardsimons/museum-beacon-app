angular.module('app.routes', [])

.config(function($stateProvider, $urlRouterProvider) {

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider

  .state('tabsController', {
    url: '/page1',
    templateUrl: 'templates/tabsController.html',
    abstract:true
  })

  .state('homePage', {
    url: '/',
    templateUrl: 'templates/homePage.html',
    controller: 'homePageCtrl'
  })

  .state('videoPage', {
    url: '/video',
    templateUrl: 'templates/videoPage.html',
    controller: 'videoPageCtrl'
  })

  .state('beaconsPage', {
    url: '/beacons',
    templateUrl: 'templates/beaconsPage.html',
    controller: 'beaconsPageCtrl'
  })

  .state('mapPage', {
    url: '/map',
    templateUrl: 'templates/mapPage.html',
    controller: 'mapPageCtrl'
  })

$urlRouterProvider.otherwise('/')

  

});