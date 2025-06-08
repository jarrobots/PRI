<template>
  <div class="wrapper">
    <div class="card">
      <h2 class="title">Podgląd grupy</h2>
      <!-- Dropdown: Lista studentów -->
      <div class="student-selector">
        <strong>Wybierz studenta:</strong>
        <select v-model="selectedStudent" class="dropdown">
          <option disabled value="">-- wybierz --</option>
          <option v-for="student in students" :key="student">{{ student }}</option>
        </select>
      </div>
      <!-- Tabela -->
      <table class="table">
        <thead>
          <tr>
            <th colspan="4">Pliki wysłane przez studenta</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(row, index) in 7" :key="index" :class="index % 2 === 0 ? 'row-light' : ''">
            <td>x</td> <!-- Placeholder -->
            <td><button class="action-btn">Dodaj komentarz</button></td> <!-- Placeholder -->
            <td>Data</td> <!-- Placeholder -->
            <td><button class="action-btn">Podgląd</button></td> <!-- Placeholder -->
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'GroupPreview',
  computed: {
    groupName() {
      return this.$route.query.grupa || 'Nie wybrano grupy'
    }
  },
  data() {
    return {
      selectedStudent: '',
      students: [
         'Patryk Piec', //Placeholder
         'Jarosław Romańczuk', //Placeholder
         'Tomasz Wasyłyk', //Placeholder
         'Bartosz Wiśniewski' //Placeholder
      ]
    }
  }
}
</script>

<style scoped>
body {
  background-color: white;
  margin: 0;
  font-family: Arial, sans-serif;
}

.wrapper {
  display: flex;
  justify-content: center;
  padding: 2rem;
  background-color: white;
  min-height: 100vh;
}

.card {
  width: 100%;
  max-width: 650px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.08);
  padding: 2rem;
}

.title {
  text-align: center;
  color: #007bff;
  font-weight: 600;
  margin-bottom: 1.5rem;
}

.student-selector {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1.5rem;
  flex-wrap: wrap;
}

.dropdown {
  padding: 0.5rem;
  border-radius: 6px;
  border: 1px solid #ccc;
  min-width: 200px;
}

.table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 1rem;
  text-align: left;
}

.table thead tr {
  background-color: #d1e3ff;
}

.table th,
.table td {
  padding: 0.8rem;
  border: 1px solid #ccc;
}

.row-light {
  background-color: #f9f9f9;
}

.action-btn {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 0.4rem 0.8rem;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.9rem;
}

.action-btn:hover {
  background-color: #0056b3;
}

.group-info {
  text-align: right;
  color: #555;
}
</style>
