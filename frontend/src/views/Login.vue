<template> 
  <div class="row">
    <div class="col d-flex justify-content-center">
      <div class="login-container">
        <h2 class="login-header">Log in With:</h2>
        <div class="row g-0">
          <div class="col-sm-auto d-flex justify-content-center align-items-center">

            <div class="form-check">
              <input class="form-check-input" type="checkbox" value="1" id="stay-signed-in">
              <label class="form-check-label" for="stay-signed-in">
                Stay Signed in
              </label>
            </div>

            <div class="vertical-rule"></div>
          </div>
          <div class="col">

            <div class="login-service">
              <img alt="Battle.net logo" src="../assets/battlenet-logo.png">
              <p>Login with Battle.net</p>
            </div>

            <div class="login-service">
              <img alt="Battle.net logo" src="../assets/battlenet-logo.png">
              <p>Login with Google</p>
            </div>
            
          </div>

        </div>
         
      </div>
    </div>
  </div>
</template>

<style>
  .login-container {
    margin-top: 60px;
    margin-bottom: 60px;
    padding: 15px;
    min-width: 420px;
    box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 1px 5px 0 rgba(0, 0, 0, 0.12), 0 3px 1px -2px rgba(0, 0, 0, 0.2);
    border-radius: 3px;
  }

  .login-container .login-header {
    margin-bottom: 2.5rem;
  }

  .login-container .vertical-rule {
    margin-left: 10px;
    margin-right: 10px;
    border-left: 1px solid rgba(0, 0, 0, 0.1);
    border-right: 1px solid rgba(255, 255, 255, 0.3);
    height: 100px;
  }
  .login-container .login-service {
    background-color: #4285f4;
    cursor: pointer;
  }
  .login-container .login-service:not(:first-child) {
     margin-top: 10px;
  }
  .login-container .login-service img {

    vertical-align: middle;
    width: 46px;

    background-color: white;

    border-style: solid;
    border-width: 1px;
    border-color: #4285f4;
    
  }
  .login-container .login-service p {
    display: inline-block;
    vertical-align: middle;
    margin: 0;
    padding-left: 10px;
    color: #ffffff;
    font-weight: bold;
  }
</style>
