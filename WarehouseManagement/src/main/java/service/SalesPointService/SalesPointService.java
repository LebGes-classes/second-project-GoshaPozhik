package service.SalesPointService;

import dao.SalesPointDAO.SalesPointDao;
import entity.SalesPoint.SalesPoint;
import java.sql.SQLException;
import java.util.List;

public class SalesPointService {
    private final SalesPointDao salesPointDao = new SalesPointDao();

    // Добавить точку продаж
    public void addSalesPoint(SalesPoint point) throws SQLException {
        salesPointDao.addSalesPoint(point);
    }

    // Обновить состояние точки (открыть/закрыть)
    public void toggleSalesPointStatus(int pointId, boolean isActive) throws SQLException {
        salesPointDao.updateSalesPointStatus(pointId, isActive);
    }

    // Список всех точек продаж
    public List<SalesPoint> getAllSalesPoints() throws SQLException {
        return salesPointDao.getAllSalesPoints();
    }
}