getUserInfo()
  .then(user => console.log(`Hello ${user.name}!`))
  .catch(err => console.error('Error fetching user info:', err));
