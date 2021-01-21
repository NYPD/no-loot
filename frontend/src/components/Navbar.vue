<template>
  <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <div class="container">
      <router-link to="/" class="navbar-brand">
        <img alt="Battle.net logo" src="../assets/battlenet-logo.png" style="width: 1.5rem;">
        No Loot
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav me-auto mb-2 mb-md-0">
          <li class="nav-item active">
            <router-link to="/" class="nav-link">Home</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/about" class="nav-link">About</router-link>
          </li>
        </ul>
        <div class="d-flex user-info">
          <ul class="navbar-nav mb-2 mb-md-0">
            <router-link to="/login" class="nav-link">Login</router-link>
          </ul>
        </div>
      </div>
    </div>
  </nav>
</template>


<script>
export default {
  name: 'Navbar'
}
</script>