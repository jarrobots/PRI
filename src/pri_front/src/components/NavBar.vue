<template>
  <div>
    <nav
  class="navbar navbar-expand-lg navbar-dark bg-primary px-3 shadow mt-1 mx-1"
  style="border-radius: 1rem;"
>


        <!-- Ikona Hamburger-->
        <button class="btn btn-primary me-3" @click="toggleSidebar">
        ☰
      </button>
      <a class="navbar-brand fw-bold" href="#">PRI</a>

      <div class="ms-auto d-flex align-items-center text-white">
        <i class="bi bi-person-circle fs-4 me-2"></i>
        <div class="d-flex flex-column">
          <span>{{ user.name }}</span>
          <small class="text-light">{{ user.role }}</small>
        </div>
      </div>
    </nav>

    <!-- Sidebar Menu -->
    <div :class="['offcanvas', sidebarOpen ? 'show' : '', 'offcanvas-start']" tabindex="-1" style="visibility: visible; background-color: #f8f9fa;">
      <div class="offcanvas-header">
        <h5 class="offcanvas-title">Menu</h5>
        <button type="button" class="btn-close text-reset" @click="toggleSidebar"></button>
      </div>
      <div class="offcanvas-body">
        <ul class="list-group">
          <li class="list-group-item border-0">
            <router-link to="/" class="text-decoration-none">Home</router-link>
          </li>

          <li class="list-group-item border-0">
            <router-link to="/review-panel" class="text-decoration-none">Panel recenzji</router-link>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NavBar',
  data() {
    return {
      sidebarOpen: false,
      user: {
        name: 'Patryk Piec', // Placeholder
        role: 'admin', // Placeholder
      },
    };
  },
  methods: {
    toggleSidebar() {
      this.sidebarOpen = !this.sidebarOpen;
    }
  },
  mounted() {
  fetch("api/v1/")
      .then((response) => response.text() )
      .then((data) => {
        this.msg = data;
      })
    fetch("api/v1/", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          id: 2,
          chapter_id: 3,
          chapter_title: "tst",
          group_id: 2
        })
      })
          .then((response) => response.text())
          .then((data) => {
            this.msg = data;
          })


      fetch("api/v1/1", {
        method: "DELETE"})
          .then((response) => response.text())
          .then((data) => {
            this.msg = data;
          })

  }
}
</script>

<style scoped>
.offcanvas {
  width: 250px;
  transform: translateX(-100%);
  transition: transform 0.3s ease-in-out;
  position: fixed;
  top: 0;
  left: 0;
  height: 100%;
  z-index: 1045;
}
.offcanvas.show {
  transform: translateX(0);
}
</style>

