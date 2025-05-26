<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>Quản lý đặt phòng</title>
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

<script>
        function toggleInput(checkboxId, inputId) {
            const checkbox = document.getElementById(checkboxId);
            const input = document.getElementById(inputId);
            input.disabled = !checkbox.checked;
        }
    </script>
</head>

<body>
	<%@ include file="fragments/header.jsp"%>
	<%@ include file="fragments/sidebar.jsp"%>

	<main id="main" class="main">

		<div class="pagetitle">
			<h1>Quản lý đặt phòng</h1>
			<nav>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="index.html">Trang chủ</a></li>
					<li class="breadcrumb-item">Quản lý</li>
					<li class="breadcrumb-item active">Đặt phòng</li>
				</ol>
			</nav>
		</div>
		<section class="section">
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
						<div class="card-body">

							<div
								class="d-flex align-items-center justify-content-start gap-2 my-3">
								<h5 class="mb-0">Bảng các đặt phòng</h5>

								<c:if test="${not empty totalPages}">
									<select class="form-select w-auto" aria-label="Page selection"
										id="pageSelect">
										<c:forEach var="page" begin="1" end="${totalPages}">
											<option value="${page}"
												<c:if test="${page == currentPage}">selected</c:if>>
												Trang ${page}</option>
										</c:forEach>
									</select>
									<script>
                                        document.addEventListener('DOMContentLoaded', () => {
                                            const selectElement = document.getElementById('pageSelect');
                                            const baseUrl = '${pageContext.request.contextPath}/booking?page=';
                                            selectElement.addEventListener('change', (event) => {
                                                const selectedPage = event.target.value;
                                                if (selectedPage) {
                                                    window.location.href = baseUrl + selectedPage;
                                                }
                                            });
                                        });
                                    </script>
									<p class="mb-0">trên tổng số ${totalPages} trang.</p>
								</c:if>

								<c:if test="${not empty totalItems}">
									<p class="mb-0">Tổng số đặt phòng: ${totalItems}</p>
								</c:if>

							</div>

							<c:if test="${not empty success}">
								<span
									class="alert alert-success bg-success text-light border-0 alert-dismissible fade show position-fixed bottom-0 end-0 m-3"
									role="alert"> ${success}
									<button type="button" class="btn-close btn-close-white"
										data-bs-dismiss="alert" aria-label="Close"></button>
								</span>
							</c:if>
							<c:if test="${not empty failure}">
								<span
									class="alert alert-danger bg-danger text-light border-0 alert-dismissible fade show position-fixed bottom-0 end-0 m-3"
									role="alert"> ${failure}
									<button type="button" class="btn-close btn-close-white"
										data-bs-dismiss="alert" aria-label="Close"></button>
								</span>
							</c:if>

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
										<th scope="col">Ghi chú khi đặt phòng</th>
										<th scope="col">Bình luận từ khách hàng</th>
										<th scope="col">Đánh giá</th>
										<th scope="col">Trạng thái</th>

									</tr>
								</thead>
								<tbody>
									<c:forEach var="row" items="${bookingList}">
										<tr>
											<td class="align-middle">${row.bookingId}</td>
											<td class="align-middle">${row.roomId}</td>
											    <td class="align-middle">${row.roomName}</td> 
											<td class="align-middle">${row.customerContact}</td>
											<td class="align-middle">${row.customerContact1}</td>
											<td class="align-middle">${row.bookingStartDate}</td>
											<td class="align-middle">${row.bookingEndDate}</td>
											<td class="align-middle">${row.bookingPeopleCount}</td>
											<td class="align-middle">${row.bookingNote}</td>
											<td class="align-middle">${row.bookingComment}</td>
											<td class="align-middle">${row.bookingRate}</td>
											<td class="align-middle"><c:choose>
													<c:when test="${row.bookingState == 1}">
														<button type="submit" class="btn btn-primary btn-action"
															disabled>Đã chấp nhận</button>
													</c:when>
													<c:when test="${row.bookingState == 0}">
														<form action="${pageContext.request.contextPath}/booking"
															method="post" style="display: inline;">
															<input type="hidden" name="action" value="reject">
															<input type="hidden" name="booking_id"
																value="${row.bookingId}">
															<button type="submit" class="btn btn-danger btn-action">
																Từ chối</button>
														</form>

														<form action="${pageContext.request.contextPath}/booking"
															method="post" style="display: inline;">
															<input type="hidden" name="action" value="accept">
															<input type="hidden" name="booking_id"
																value="${row.bookingId}">
															<button type="submit" class="btn btn-success btn-action">
																Chấp nhận</button>
														</form>
													</c:when>
													<c:when test="${row.bookingState == -1}">
														<button type="submit" class="btn btn-secondary btn-action"
															disabled>Đã từ chối</button>
													</c:when>
													<c:otherwise>
                                                        Unknown
                                                    </c:otherwise>
												</c:choose></td>
										</tr>
									</c:forEach>

								</tbody>
							</table>

						</div>
					</div>
				</div>
			</div>
		</section>
	</main>
	<!-- End #main -->

	<!-- ======= Footer ======= -->
	<footer id="footer" class="footer"> </footer>
	<!-- End Footer -->

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
