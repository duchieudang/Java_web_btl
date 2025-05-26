<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<script>
console.log("${requestScope.avg_rating_thismonth}")
console.log("${requestScope.avg_rating_thisweek}")
console.log("${requestScope.avg_rating_today}")
</script>



<head>
<title>Trang chủ - Daskboard</title>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<meta content="" name="description">
<meta content="" name="keywords">

<!-- Favicons -->
<link href="assets/img/favicon.png" rel="icon">
<link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

<!-- Google Fonts -->
<link href="https://fonts.gstatic.com" rel="preconnect">
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">

<!-- Vendor CSS Files -->
<link href="assets/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="assets/vendor/bootstrap-icons/bootstrap-icons.css"
	rel="stylesheet">
<link href="assets/vendor/boxicons/css/boxicons.min.css"
	rel="stylesheet">
<link href="assets/vendor/quill/quill.snow.css" rel="stylesheet">
<link href="assets/vendor/quill/quill.bubble.css" rel="stylesheet">
<link href="assets/vendor/remixicon/remixicon.css" rel="stylesheet">
<link href="assets/vendor/simple-datatables/style.css" rel="stylesheet">

<!-- Template Main CSS File -->
<link href="assets/css/style.css" rel="stylesheet">
</head>

<body>



	<%@ include file="fragments/header.jsp"%>

	<%@ include file="fragments/sidebar.jsp"%>

	<main id="main" class="main">

		<div class="pagetitle">
			<h1>Dashboard</h1>
			<nav>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="index.html">Trang chủ</a></li>
					<li class="breadcrumb-item active">Dashboard</li>
				</ol>
			</nav>
		</div>
		<!-- End Page Title -->

		<section class="section dashboard">
			<div class="row">

				<!-- Left side columns -->
				<div class="col-lg-8">
					<div class="row">

						<script>
						function updateStats(containerId, timeFrameId, bookingToday, bookingThisWeek, bookingThisMonth, timeframe) {
						    document.getElementById(timeFrameId).innerText = `| ` + timeframe;
						    let bookingCount = bookingToday;

						    if (timeframe === 'Hôm nay') {
						        bookingCount = bookingToday;
						    } else if (timeframe === 'Tuần này') {
						        bookingCount = bookingThisWeek;
						    } else if (timeframe === 'Tháng này') {
						        bookingCount = bookingThisMonth;
						    }

						    document.getElementById(containerId).innerText = bookingCount;
						}
						</script>

						<!-- Sales Card -->
						<div class="col-xxl-4 col-md-6">
							<div class="card info-card sales-card">
								<div class="filter">
									<a class="icon" href="#" data-bs-toggle="dropdown"><i
										class="bi bi-three-dots"></i></a>
									<ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
										<li class="dropdown-header text-start"><h6>Bộ lọc</h6></li>
										<li><a class="dropdown-item"
											onclick="updateStats('booking-count-1', 'time-frame-1', ${requestScope.booking_today}, ${requestScope.booking_thisweek}, ${requestScope.booking_thismonth}, 'Hôm nay')">Hôm
												nay</a></li>
										<li><a class="dropdown-item"
											onclick="updateStats('booking-count-1', 'time-frame-1', ${requestScope.booking_today}, ${requestScope.booking_thisweek}, ${requestScope.booking_thismonth}, 'Tuần này')">Tuần
												này</a></li>
										<li><a class="dropdown-item"
											onclick="updateStats('booking-count-1', 'time-frame-1', ${requestScope.booking_today}, ${requestScope.booking_thisweek}, ${requestScope.booking_thismonth}, 'Tháng này')">Tháng
												này</a></li>
									</ul>
								</div>

								<div class="card-body">
									<h5 class="card-title">
										Số đơn đặt phòng <span id="time-frame-1">| Hôm nay</span>
									</h5>

									<div class="d-flex align-items-center">
										<div
											class="card-icon rounded-circle d-flex align-items-center justify-content-center">
											<i class="bi bi-cart"></i>
										</div>
										<div class="ps-3">
											<h6>
												<span id="booking-count-1">${requestScope.booking_today}</span>
												đơn
											</h6>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- End Sales Card -->

						<!-- Revenue Card -->
						<div class="col-xxl-4 col-md-6">
							<div class="card info-card revenue-card">

								<div class="filter">
									<a class="icon" href="#" data-bs-toggle="dropdown"><i
										class="bi bi-three-dots"></i></a>
									<ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
										<li class="dropdown-header text-start">
											<h6>Filter</h6>
										</li>

										<li><a class="dropdown-item"
											onclick="updateStats('booking-count-2', 'time-frame-2', ${requestScope.revenue_today}, ${requestScope.revenue_thisweek}, ${requestScope.revenue_thismonth}, 'Hôm nay')">Hôm
												nay</a></li>
										<li><a class="dropdown-item"
											onclick="updateStats('booking-count-2', 'time-frame-2', ${requestScope.revenue_today}, ${requestScope.revenue_thisweek}, ${requestScope.revenue_thismonth}, 'Tuần này')">Tuần
												này</a></li>
										<li><a class="dropdown-item"
											onclick="updateStats('booking-count-2', 'time-frame-2', ${requestScope.revenue_today}, ${requestScope.revenue_thisweek}, ${requestScope.revenue_thismonth}, 'Tháng này')">Tháng
												này</a></li>
									</ul>
								</div>

								<div class="card-body">
									<h5 class="card-title">
										Doanh thu <span id="time-frame-2">| Hôm nay</span>
									</h5>

									<div class="d-flex align-items-center">
										<div
											class="card-icon rounded-circle d-flex align-items-center justify-content-center">
											<i class="bi bi-currency-dollar"></i>
										</div>
										<div class="ps-3">
											<h6>
												<span id="booking-count-2">${requestScope.revenue_today}</span>
												VND
											</h6>

										</div>
									</div>
								</div>

							</div>
						</div>
						<!-- End Revenue Card -->

						<!-- Customers Card -->
						<div class="col-xxl-4 col-xl-12">

							<div class="card info-card customers-card">

								<div class="filter">
									<a class="icon" href="#" data-bs-toggle="dropdown"><i
										class="bi bi-three-dots"></i></a>
									<ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
										<li class="dropdown-header text-start">
											<h6>Filter</h6>
										</li>

										<li><a class="dropdown-item"
											onclick="updateStats('booking-count-3', 'time-frame-3', ${requestScope.avg_rating_today}, ${requestScope.avg_rating_thisweek}, ${requestScope.avg_rating_thismonth}, 'Hôm nay')">Hôm
												nay</a></li>
										<li><a class="dropdown-item"
											onclick="updateStats('booking-count-3', 'time-frame-3', ${requestScope.avg_rating_today}, ${requestScope.avg_rating_thisweek}, ${requestScope.avg_rating_thismonth}, 'Tuần này')">Tuần
												này</a></li>
										<li><a class="dropdown-item"
											onclick="updateStats('booking-count-3', 'time-frame-3', ${requestScope.avg_rating_today}, ${requestScope.avg_rating_thisweek}, ${requestScope.avg_rating_thismonth}, 'Tháng này')">Tháng
												này</a></li>
									</ul>
								</div>

								<div class="card-body">
									<h5 class="card-title">
										Số sao <span id="time-frame-3">| Hôm nay</span>
									</h5>

									<div class="d-flex align-items-center">
										<div
											class="card-icon rounded-circle d-flex align-items-center justify-content-center">
											<i class="bi bi-people"></i>
										</div>
										<div class="ps-3">
											<h6>
												<span id="booking-count-3">${requestScope.avg_rating_today}</span>
												sao
											</h6>


										</div>
									</div>

								</div>
							</div>

						</div>
						<!-- End Customers Card -->

						<!-- Reports -->
						<div class="col-12">
							<div class="card">
					            <div class="card-body">
					              <h5 class="card-title">Số lượt đặt phòng trong 7 ngày qua</h5>
					
					              <!-- Line Chart -->
					              <canvas id="lineChart" style="max-height: 400px; display: block; box-sizing: border-box; height: 248px; width: 496px;" width="992" height="496"></canvas>
					              <script>
					                document.addEventListener("DOMContentLoaded", () => {
					                  new Chart(document.querySelector('#lineChart'), {
					                    type: 'line',
					                    data: {
					                      labels: [
					                    	  
					                    	  <c:forEach var="date" items="${booking_dates}">
							                    '${date}',
							                  </c:forEach>
					                    	  
					                    	  ],
					                      datasets: [{
					                        label: 'Line Chart',
					                        data: [
					                        	<c:forEach var="count" items="${booking_counts}">
								                    '${count}',
								                  </c:forEach>
					                        ],
					                        fill: false,
					                        borderColor: 'rgb(75, 192, 192)',
					                        tension: 0.1
					                      }]
					                    },
					                    options: {
					                      scales: {
					                        y: {
					                          beginAtZero: true
					                        }
					                      }
					                    }
					                  });
					                });
					              </script>
					              <!-- End Line CHart -->
					
					            </div>
					          </div>
						</div>
						<!-- End Reports -->

						<!-- Recent Sales -->
						<div class="col-12">
							<div class="card recent-sales overflow-auto">


								<div class="card-body">
									<h5 class="card-title">
										Hoạt động đặt phòng gần đây
									</h5>

										<table class="table datatable">
								<thead>
									<tr>
										<th scope="col" class="align-middle">ID</th>
										<th scope="col">Mã Phòng</th>
										    <th scope="col">Tên Phòng</th>
										<th scope="col">Thông tin liên lạc KH</th>
										<th scope="col">CCCD</th>
										<th scope="col">Từ ngày</th>
										<th scope="col">Đến ngày</th>
										<th scope="col">Số người</th>
									
										
								
									</tr>
								</thead>
								<tbody>
									<c:forEach var="row" items="${booking}">
										<tr>
											<td class="align-middle">${row.bookingId}</td>
											<td class="align-middle">${row.roomId}</td>
											     <td class="align-middle">${row.roomName}</td> 
											<td class="align-middle">${row.customerContact}</td>
											<td class="align-middle">${row.customerContact1}</td>
											<td class="align-middle">${row.bookingStartDate}</td>
											<td class="align-middle">${row.bookingEndDate}</td>
											<td class="align-middle">${row.bookingPeopleCount}</td>
										
									
										</tr>
									</c:forEach>

								</tbody>
							</table>

								</div>

							</div>
						</div>
						<!-- End Recent Sales -->

						<!-- Top Selling -->
<!-- 						<div class="col-12"> -->
<!-- 							<div class="card top-selling overflow-auto"> -->

<!-- 								<div class="filter"> -->
<!-- 									<a class="icon" href="#" data-bs-toggle="dropdown"><i -->
<!-- 										class="bi bi-three-dots"></i></a> -->
<!-- 									<ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow"> -->
<!-- 										<li class="dropdown-header text-start"> -->
<!-- 											<h6>Filter</h6> -->
<!-- 										</li> -->

<!-- 										<li><a class="dropdown-item" href="#">Today</a></li> -->
<!-- 										<li><a class="dropdown-item" href="#">This Month</a></li> -->
<!-- 										<li><a class="dropdown-item" href="#">This Year</a></li> -->
<!-- 									</ul> -->
<!-- 								</div> -->

<!-- 								<div class="card-body pb-0"> -->
<!-- 									<h5 class="card-title"> -->
<!-- 										Top Selling <span>| Today</span> -->
<!-- 									</h5> -->

<!-- 									<table class="table table-borderless"> -->
<!-- 										<thead> -->
<!-- 											<tr> -->
<!-- 												<th scope="col">Preview</th> -->
<!-- 												<th scope="col">Product</th> -->
<!-- 												<th scope="col">Price</th> -->
<!-- 												<th scope="col">Sold</th> -->
<!-- 												<th scope="col">Revenue</th> -->
<!-- 											</tr> -->
<!-- 										</thead> -->
<!-- 										<tbody> -->
<!-- 											<tr> -->
<!-- 												<th scope="row"><a href="#"><img -->
<!-- 														src="assets/img/product-1.jpg" alt=""></a></th> -->
<!-- 												<td><a href="#" class="text-primary fw-bold">Ut -->
<!-- 														inventore ipsa voluptas nulla</a></td> -->
<!-- 												<td>$64</td> -->
<!-- 												<td class="fw-bold">124</td> -->
<!-- 												<td>$5,828</td> -->
<!-- 											</tr> -->
<!-- 											<tr> -->
<!-- 												<th scope="row"><a href="#"><img -->
<!-- 														src="assets/img/product-2.jpg" alt=""></a></th> -->
<!-- 												<td><a href="#" class="text-primary fw-bold">Exercitationem -->
<!-- 														similique doloremque</a></td> -->
<!-- 												<td>$46</td> -->
<!-- 												<td class="fw-bold">98</td> -->
<!-- 												<td>$4,508</td> -->
<!-- 											</tr> -->
<!-- 											<tr> -->
<!-- 												<th scope="row"><a href="#"><img -->
<!-- 														src="assets/img/product-3.jpg" alt=""></a></th> -->
<!-- 												<td><a href="#" class="text-primary fw-bold">Doloribus -->
<!-- 														nisi exercitationem</a></td> -->
<!-- 												<td>$59</td> -->
<!-- 												<td class="fw-bold">74</td> -->
<!-- 												<td>$4,366</td> -->
<!-- 											</tr> -->
<!-- 											<tr> -->
<!-- 												<th scope="row"><a href="#"><img -->
<!-- 														src="assets/img/product-4.jpg" alt=""></a></th> -->
<!-- 												<td><a href="#" class="text-primary fw-bold">Officiis -->
<!-- 														quaerat sint rerum error</a></td> -->
<!-- 												<td>$32</td> -->
<!-- 												<td class="fw-bold">63</td> -->
<!-- 												<td>$2,016</td> -->
<!-- 											</tr> -->
<!-- 											<tr> -->
<!-- 												<th scope="row"><a href="#"><img -->
<!-- 														src="assets/img/product-5.jpg" alt=""></a></th> -->
<!-- 												<td><a href="#" class="text-primary fw-bold">Sit -->
<!-- 														unde debitis delectus repellendus</a></td> -->
<!-- 												<td>$79</td> -->
<!-- 												<td class="fw-bold">41</td> -->
<!-- 												<td>$3,239</td> -->
<!-- 											</tr> -->
<!-- 										</tbody> -->
<!-- 									</table> -->

<!-- 								</div> -->

<!-- 							</div> -->
<!-- 						</div> -->
						<!-- End Top Selling -->

					</div>
				</div>
				<!-- End Left side columns -->

				<!-- Right side columns -->
				<div class="col-lg-4">

					<!-- Recent Activity -->
					
					<!-- End Recent Activity -->

					<!-- Budget Report -->
					<div class="card">
			            <div class="card-body">
			              <h5 class="card-title">Phòng theo số sao</h5>
			
			              <!-- Bar Chart -->
			              <canvas id="barChart" style="max-height: 400px; display: block; box-sizing: border-box; height: 248px; width: 496px;" width="992" height="496"></canvas>
			              <script>
			                document.addEventListener("DOMContentLoaded", () => {
			                  new Chart(document.querySelector('#barChart'), {
			                    type: 'bar',
			                    data: {
			                      labels: ['1 sao', '2 sao', '3 sao', '4 sao', '5 sao'],
			                      datasets: [{
			                        label: 'Số lượng',
			                        data: [
			                        	// 65, 59, 80, 81, 56, 55, 40 // abcdef
			                        	<c:forEach var="count" items="${room_count}">
							                  ${count}, 
						                  </c:forEach>
			                        ],
			                        backgroundColor: [
			                          'rgba(255, 99, 132, 0.2)',
			                          'rgba(255, 159, 64, 0.2)',
			                          'rgba(255, 205, 86, 0.2)',
			                          'rgba(75, 192, 192, 0.2)',
			                          'rgba(54, 162, 235, 0.2)',
			                          'rgba(153, 102, 255, 0.2)',
			                          'rgba(201, 203, 207, 0.2)'
			                        ],
			                        borderColor: [
			                          'rgb(255, 99, 132)',
			                          'rgb(255, 159, 64)',
			                          'rgb(255, 205, 86)',
			                          'rgb(75, 192, 192)',
			                          'rgb(54, 162, 235)',
			                          'rgb(153, 102, 255)',
			                          'rgb(201, 203, 207)'
			                        ],
			                        borderWidth: 1
			                      }]
			                    },
			                    options: {
			                      scales: {
			                        y: {
			                          beginAtZero: true
			                        }
			                      }
			                    }
			                  });
			                });
			              </script>
			              <!-- End Bar CHart -->
			
			            </div>
			          </div>
					<!-- End Budget Report -->

					<!-- Website Traffic -->
					<div class="card">
						<div class="card-body pb-0">
							<h5 class="card-title">
								Tỉ lệ đánh giá
							</h5>

							<div id="trafficChart" style="min-height: 400px;" class="echart"></div>

							<script>
                document.addEventListener("DOMContentLoaded", () => {
                  echarts.init(document.querySelector("#trafficChart")).setOption({
                    tooltip: {
                      trigger: 'item'
                    },
                    legend: {
                      top: '5%',
                      left: 'center'
                    },
                    series: [{
                      name: 'Access From',
                      type: 'pie',
                      radius: ['40%', '70%'],
                      avoidLabelOverlap: false,
                      label: {
                        show: false,
                        position: 'center'
                      },
                      emphasis: {
                        label: {
                          show: true,
                          fontSize: '18',
                          fontWeight: 'bold'
                        }
                      },
                      labelLine: {
                        show: false
                      },
                      data: [
                    	  <c:forEach var="booking_rate" items="${booking_rates}">
			                  {
			                	  value: ${booking_rate_counts[booking_rates.indexOf(booking_rate)]}, 
			                	  name: '${booking_rate} sao'
			                		  
			                  },
		                  </c:forEach>
                    	  
                    	  
//                     	  {
//                           value: 1048,
//                           name: 'Search Engine'
//                         },
//                         {
//                           value: 735,
//                           name: 'Direct'
//                         },
//                         {
//                           value: 580,
//                           name: 'Email'
//                         },
//                         {
//                           value: 484,
//                           name: 'Union Ads'
//                         },
//                         {
//                           value: 300,
//                           name: 'Video Ads'
//                         }
                      ]
                    }]
                  });
                });
              </script>

						</div>
					</div>
					<!-- End Website Traffic -->

					<!-- News & Updates Traffic -->
					
					<!-- End News & Updates -->

				</div>
				<!-- End Right side columns -->

			</div>
		</section>

	</main>
	<!-- End #main -->

	<!-- ======= Footer ======= -->
	<footer id="footer" class="footer">
<!-- 		<div class="copyright"> -->
<!-- 			&copy; Copyright <strong><span>NiceAdmin</span></strong>. All Rights -->
<!-- 			Reserved -->
<!-- 		</div> -->
<!-- 		<div class="credits"> -->
			<!-- All the links in the footer should remain intact. -->
			<!-- You can delete the links only if you purchased the pro version. -->
			<!-- Licensing information: https://bootstrapmade.com/license/ -->
			<!-- Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/ -->
<!-- 			Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a> -->
<!-- 		</div> -->
	</footer>
	<!-- End Footer -->

	<a href="#"
		class="back-to-top d-flex align-items-center justify-content-center"><i
		class="bi bi-arrow-up-short"></i></a>

	<!-- Vendor JS Files -->
	<script src="assets/vendor/apexcharts/apexcharts.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="assets/vendor/chart.js/chart.umd.js"></script>
	<script src="assets/vendor/echarts/echarts.min.js"></script>
	<script src="assets/vendor/quill/quill.js"></script>
	<script src="assets/vendor/simple-datatables/simple-datatables.js"></script>
	<script src="assets/vendor/tinymce/tinymce.min.js"></script>
	<script src="assets/vendor/php-email-form/validate.js"></script>

	<!-- Template Main JS File -->
	<script src="assets/js/main.js"></script>

</body>

</html>