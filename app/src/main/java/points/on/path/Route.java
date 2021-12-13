package points.on.path;

import java.util.ArrayList;

public class Route {

    private Point source;
    private Point destination;
    private double total;
    private double distanceCovered;
    public ArrayList<Point> points = new ArrayList<Point>();

    public Route(Point source, Point destination, double total_distance){
        this.source = source;
        this.destination = destination;
        this.total = total_distance;
    }

    public static double toRadian(double coordinate){
        return coordinate * Math.PI / 180;
    }

    public static double toDegree(double coordinate){
        return coordinate * 180 / Math.PI;
    }

    public ArrayList<Point> moveAlong(Point point, int distance){
        Point new_point = moveTowards(this.source, distance);
        while(true){
            if (new_point != null){
                points.add(new_point);
                new_point = moveTowards(new_point, distance);

            }
            else{
                break;
            }
        }
        return points;
        
    }

    public Point moveTowards(Point point, int distance){
        if (distanceCovered + distance < total){
            double lat1 = toRadian(point.latitude);
            double lon1 = toRadian(point.longitude);
            double lat2 = toRadian(this.destination.latitude);
            double lon2 = toRadian(this.destination.longitude);
            double dLon = toRadian(point.longitude - this.destination.longitude);

            // Find the bearing from this point to the next.
            double brng = Math.atan2(Math.sin(dLon) * Math.cos(lat2),
                        Math.cos(lat1) * Math.sin(lat2) -
                        Math.sin(lat1) * Math.cos(lat2) * 
                        Math.cos(dLon));

            double angDist = distance / 6371000;  // Earth's radius.

            // Calculate the destination point, given the source and bearing.
            lat2 = Math.asin(Math.sin(lat1) * Math.cos(angDist) + 
            Math.cos(lat1) * Math.sin(angDist) * 
            Math.cos(brng));

            lon2 = lon1 + Math.atan2(Math.sin(brng) * Math.sin(angDist) *
                    Math.cos(lat1), 
                    Math.cos(angDist) - Math.sin(lat1) *
                    Math.sin(lat2));

            if (lat2 == Double.NaN|| lon2 == Double.NaN) {
                return null;
            }
            this.distanceCovered += distance;
            return new Point(toDegree(lat2), toDegree(lon2));
        }
        else{
            // total distance already covered
            return null;
        }
    }
    
}
