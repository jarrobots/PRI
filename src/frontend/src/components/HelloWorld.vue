<template>
<h1>{{msg}}</h1>
</template>

<script>
export default {
  name: 'HelloWorld',
  data() {
    return {
      msg: ''
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

