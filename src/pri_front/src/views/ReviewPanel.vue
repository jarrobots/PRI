<template>
  <div class="container mt-5">
    <h2 class="mb-4">Panel recenzji</h2>

    <div class="row mb-3">
      <!-- Dropdown: Lista grup -->
      <div class="col-md-6">
        <label for="groupSelect" class="form-label">Lista grup</label>
        <select
          id="groupSelect"
          class="form-select"
          v-model="selectedGroup"
        >
          <option disabled value="">Wybierz grupę</option>
          <option v-for="group in groups" :key="group">{{ group }}</option>
        </select>
      </div>

      <!-- Dropdown: Wybierz rozdział -->
      <div class="col-md-6">
        <label for="chapterSelect" class="form-label">Wybierz rozdział</label>
        <select
          id="chapterSelect"
          class="form-select"
          v-model="selectedChapter"
        >
          <option disabled value="">Wybierz rozdział</option>
          <option v-for="chapter in chapters" :key="chapter">{{ chapter }}</option>
        </select>
      </div>
    </div>

    <!-- Przycisk: Podgląd grupy -->
    <div class="mb-4">
      <button
        class="btn btn-outline-primary"
        :disabled="!selectedGroup"
        @click="previewGroup"
      >
        Podgląd grupy
      </button>
    </div>

    <!-- Placeholder -->
    <div class="text-muted">
      <p>Wybrana grupa: {{ selectedGroup || '–' }}</p>
      <p>Wybrany rozdział: {{ selectedChapter || '–' }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReviewPanel',
  data() {
    return {
      selectedGroup: '',
      selectedChapter: '',
      groups: ['Grupa A', 'Grupa B', 'Grupa C'], //Placeholder
      chapters: ['Rozdział 1', 'Rozdział 2', 'Rozdział 3'], //Placeholder
    }
  },
  methods: {
    previewGroup() {
      this.$router.push({
      name: 'GroupPreview',
      query: { grupa: this.selectedGroup }
    });
    }
  }
}
</script>
